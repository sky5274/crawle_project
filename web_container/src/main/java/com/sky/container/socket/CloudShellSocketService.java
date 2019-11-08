package com.sky.container.socket;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.jcraft.jsch.JSchException;
import com.sky.container.socket.server.ShellService;
import com.sky.pub.ResultCode;
import com.sky.pub.ResultUtil;

/**
 * openssh cloud socket service
 * @author 王帆
 * @date  2019年10月26日 下午5:12:20
 */
@ServerEndpoint(value = "/cloud/shell")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class CloudShellSocketService {
	private static Map<String, CloudShellSocketService>  cacheServer=new HashMap<String, CloudShellSocketService>();
	private Log log=LogFactory.getLog(getClass());
	
    private Session session;
    private String key=null;
    private ShellService shell;

    /**
     * 	连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.err.println(JSON.toJSONString(session.getRequestParameterMap()));
        Map<String, String> paramMap = WebSocketUtil.getQueryMap(session);
        List<String> errors=new LinkedList<String>();
        if(paramMap.get("user")==null)  	errors.add("登录用户");
        if(paramMap.get("passwd")==null)  errors.add("用户密码");
        if(!CollectionUtils.isEmpty(errors)) {
        	try {
				sendMessage(JSON.toJSONString(ResultUtil.getFailed(ResultCode.PARAM_VALID, "shell 链接校验："+String.join(",", errors)+"为空")));
			} catch (IOException e) {
			}
        }else {
        	//用户校验用户后，开始连接openssh-server
        	shell=new ShellService("127.0.0.1",
        							paramMap.get("port")==null? 22:Integer.valueOf(paramMap.get("port").toString()),
				        			paramMap.get("user").toString(), 
				        			paramMap.get("passwd").toString(),
				        			session);
        	try {
        		key=JSON.toJSONString(paramMap);
				try {
					sendMessage("cloud shell connect\r\n");
				} catch (IOException e) {
				}
				shell.run();
				cacheServer.put(key, this);
			} catch (JSchException e) {
				e.printStackTrace();
				shell=null;
				try {
					sendMessage(e.getMessage());
//					sendMessage(JSON.toJSONString(ResultUtil.getFailed(ResultCode.PARAM_VALID, "shell 链接错误")));
					session.close();
				} catch (IOException e1) {
				}
			}
        }
    }
    
    /**
     * 	连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
    	cacheServer.remove(key);
    	if(shell!=null) {
    		shell.close();
    	}
    }

    /**
     * 	收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
    	if(shell!=null) {
    		shell.put(message);
    	}
    }

    /**
     *	 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
    	log.error("cloud shell has throwable,session:"+JSON.toJSONString(session.getRequestParameterMap()),error);
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

}
