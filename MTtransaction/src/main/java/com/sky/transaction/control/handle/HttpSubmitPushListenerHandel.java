package com.sky.transaction.control.handle;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import com.alibaba.fastjson.JSON;
import com.sky.transaction.bean.ConnectTransationNodeData;
import com.sky.transaction.datasource.factory.ConnectTransactionNodeFactory;
import com.sky.transaction.util.SpringMTUtil;

/**
 * 	http 事务提交 监听机制的数据处理器，使用websocket做数据处理
 * @author 王帆
 * @date  2019年9月26日 上午9:00:12
 */
public class HttpSubmitPushListenerHandel extends HttpSubmitHandle implements ConnectSubmitPushListenerHandle{
	private Log log=LogFactory.getLog(getClass());
	private WebSocketClient socketClient;
	private String baseUrl="/socket/transaction";
	
	public HttpSubmitPushListenerHandel(String platfromUrl, Long timeOut) {
		super(platfromUrl,timeOut);
		//尝试连接事务平台socket
		getSocketClient();
	}
	
	/**
     * 获取客户端连接实例
     * @param uri
     * @return
     */
    public WebSocketClient getClient(String uri){
        try {
            //创建客户端连接对象
            WebSocketClient client = new WebSocketClient(new URI(uri),new Draft_6455()) {
                /**
                 * 建立连接调用
                 * @param serverHandshake
                 */
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    log.info("===建立连接===");
                }
                /**
                 * 收到服务端消息调用
                 * @param s
                 */
                @Override
                public void onMessage(String s) {
                    log.info("====收到来自服务端的消息===" + s);
                    ConnectTransationNodeData nodedata = JSON.parseObject(s,ConnectTransationNodeData.class);
                    if(nodedata!=null) {
                    	excuteNode(nodedata);
                    }
                }
                /**
                 * 断开连接调用
                 * @param code
                 * @param reason
                 * @param remote
                 */
                @Override
                public void onClose(int code, String reason, boolean remote) {
                    log.info("关闭连接:::" + "code = " + code + ":::reason = " + reason +":::remote = " + remote);
                    socketClient=null;
                }
                /**
                 * 连接报错调用
                 * @param e
                 */
                @Override
                public void onError(Exception e) {
                    log.error("====出现错误====" + e.getMessage());
                }
            };
            //请求与服务端建立连接
            client.connect();
            //判断连接状态，0为请求中  1为已建立  其它值都是建立失败
            while(client.getReadyState().ordinal() == 0){
                try {
                    Thread.sleep(200);
                }catch (Exception e){
                    log.warn("延迟操作出现问题，但并不影响功能");
                }
                log.info("连接中.......");
            }
            //连接状态不再是0请求中，判断建立结果是不是1已建立
            if (client.getReadyState().ordinal() == 1){
                return client;
            }
        }catch (URISyntaxException e){
            log.error(e.getMessage());
        }
        return null;
    }


	@Override
	public void excuteNode(ConnectTransationNodeData node) {
		ConnectSubmitPullHandleDecorator.excuteTransaction(node);
	}
	
	private WebSocketClient getSocketClient() {
		if(socketClient==null) {
			String url = String.format("%s%s?project=%s&verson=%s",platfromUrl,baseUrl,
					SpringMTUtil.getEvnProperty(ConnectTransactionNodeFactory.NODE_ENV_PRO, ConnectTransactionNodeFactory.NODE_ENV_PRO),
					SpringMTUtil.getEvnProperty(ConnectTransactionNodeFactory.NODE_ENV_EVERSION, ConnectTransactionNodeFactory.NODE_ENV_EVERSION)
					);
			socketClient=getClient(url);
			if(socketClient==null) {
				throw new RuntimeException("websocket connect url:"+url+" failed");
			}
		}
		return socketClient;
	}
	
	public void sendMessage(String message) {
		getSocketClient().send(message);
	}
	
	@Override
	public ConnectTransationNodeData submitTransactionNodeEvent(ConnectTransationNodeData req) {
		sendMessage(JSON.toJSONString(req));
		return req;
	}
}
