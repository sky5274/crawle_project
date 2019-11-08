package com.sky.container.socket;

import java.io.IOException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sky.container.util.CommandUtil;

/**
 * win/linux docker 命令
 * @author 王帆
 * @date  2019年10月26日 下午5:06:12
 */
@ServerEndpoint(value = "/cloud/exec")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class CloudExecSocketService {
	private String defFlag="$ ";
	
    /**
     * 	连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
    	try {
			session.getBasicRemote().sendText("welcome user cloud exec shell \r\n"+defFlag);
		} catch (IOException e) {
		}
    }
    
    /**
     * 	连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
    }

    /**
     * 	收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
    	try {
    		if(!StringUtils.isEmpty(message)) {
    			session.getBasicRemote().sendText(CommandUtil.exec(message)+defFlag);
    		}
		} catch (IOException e) {
		}
    }

    /**
     *	 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
