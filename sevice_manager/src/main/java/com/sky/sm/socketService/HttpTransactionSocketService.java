package com.sky.sm.socketService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.sky.pub.util.SpringUtil;
import com.sky.sm.bean.ProjectTransationNodeData;
import com.sky.sm.service.impl.StringJsonRedisServiceImpl;

@ServerEndpoint(value = "/socket/transaction")
@Component
public class HttpTransactionSocketService {
	private StringJsonRedisServiceImpl stringRedisService;
	private int moreTime=1000*30;
	/**事务节点redis key format*/
	public static String transactionKeyFormat="%s%s-%s:%s";
	
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<HttpTransactionSocketService> webSocketSet = new CopyOnWriteArraySet<HttpTransactionSocketService>();

    private String groupKey;
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 	连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        Map<String, String> paramMap = getQueryMap(session);
        System.err.println(paramMap);
        groupKey=fromateGroup(paramMap.get("project"),paramMap.get("version"));
        System.err.println(JSON.toJSONString(this));
        webSocketSet.add(this);     //加入set中
    }
    
    private Map<String, String> getQueryMap(Session session){
    	Map<String, String> queryMap=new HashMap<String, String>();
    	String queryString = session.getQueryString();
    	if(!StringUtils.isEmpty(queryString)) {
    		String[] queryParams = queryString.split("&");
    		if(queryParams.length>0) {
    			for(String params:queryParams) {
    				if(!StringUtils.isEmpty(params)) {
    					String[] paramEntity = params.split("=");
    					if(paramEntity.length==2) {
    						queryMap.put(paramEntity[0], paramEntity[1]);
    					}
    				}
    			}
    		}
    	}
    	return queryMap;
    }
    
    public static String fromateGroup(String project,String version) {
    	return String.format("%s-%s", project,version);
    }
    
    /**
     * 	连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
    }

    /**
     * 	收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
    	ProjectTransationNodeData nodeData = JSON.parseObject(message,ProjectTransationNodeData.class);
    	if(nodeData!=null) {
    		//在redis中缓存事务节点数据
    		getStringRedisService().set(getKey(nodeData), message, Integer.valueOf((nodeData.getTimeOut()+moreTime)+""));
    		String key = fromateGroup(nodeData.getProject(), nodeData.getVersion());
    		sendInfo(key, message);
    	}
    }
    
    private StringJsonRedisServiceImpl getStringRedisService() {
    	if(stringRedisService==null) {
    		stringRedisService=SpringUtil.getBean(StringJsonRedisServiceImpl.class);
    	}
    	return stringRedisService;
    }

    /**
     *	 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
    public String getGroupKey() {
		return groupKey;
	}

	public void setGroupKey(String groupKey) {
		this.groupKey = groupKey;
	}

	public static String getKey(ProjectTransationNodeData data) {
		return String.format(transactionKeyFormat, data.getProject(),data.getVersion(),data.getGroupId(),data.getNodeId());
	}
	
    /**
     *	 群发自定义消息
     * */
    public static void sendInfo(String message) {
        for (HttpTransactionSocketService item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }
    public static void sendInfo(String groupKey,String message)  {
    	for (HttpTransactionSocketService item : webSocketSet) {
    		if(groupKey==null || !groupKey.equals(item.getGroupKey())) {
    			continue;
    		}
    		try {
    			item.sendMessage(message);
    		} catch (IOException e) {
    			continue;
    		}
    	}
    }

	
}