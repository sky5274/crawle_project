package com.sky.container.socket;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.sky.container.bean.ContainerBean;
import com.sky.container.bean.ContainerStatsBean;
import com.sky.container.service.CloudContainerService;
import com.sky.container.socket.stream.StreamReadEvent;
import com.sky.container.util.CommandUtil;
import com.sky.pub.Result;
import com.sky.pub.ResultUtil;
import com.sky.pub.util.SpringUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * docker container montior socket service
 * @author 王帆
 * @date  2019年10月26日 下午5:06:12
 */
@ServerEndpoint(value = "/cloud/container/montior")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
@Slf4j
public class ContainerMontiorSocketService {
	private boolean flag=true;
	private Session session;

	/**
	 * 	连接建立成功调用的方法
	 */
	@OnOpen
	public void onOpen(Session session) {
		this.session=session;
		flag=true;
		boolean isStart=true;    //容器是否运行
		List<String> params=session.getRequestParameterMap().get("id");
		String containerId=null;
		if(!CollectionUtils.isEmpty(params)) {
			containerId=params.get(0);
		}
		InputStream in=null;
		try {
			if(containerId !=null) {
				ContainerBean container = SpringUtil.getBean(CloudContainerService.class).queryContainerDocker(containerId);
				if(container==null || container.getContainer()==null || container.getContainer().get("State")==null) {
					sendMessage(new Result<>("c_stop_-1", "容器不存在",false));
					isStart=false;
				}else if(!JSON.parseObject(JSON.toJSONString(container.getContainer().get("State"))).getBooleanValue("Running")){
					sendMessage(new Result<>("c_stop_0", "容器已停止",false));
					isStart=false;
				}
			}else {
				containerId=null;
			}
			if(isStart) {
				//docker 监听命令
				Process proc = CommandUtil.run(String.format("docker stats %s",containerId),null);
				List<String> colums=new LinkedList<String>();
				//将docker stats 命令输出的内容 转换成监听数据对外输出
				in = proc.getInputStream();
				CommandUtil.readStream(in, new StreamReadEvent() {

					@Override
					public void invoke(String str) throws IOException {
						//CONTAINER ID , NAME ,CPU %   , MEM USAGE / LIMIT ,MEM %, NET I/O,  BLOCK I/O, PIDS
						if(!StringUtils.isEmpty(str)) {
							if(!str.contains("CONTAINER ID")) {
								colums.clear();
								String[] cols = str.split("   ");
								for(String s:cols) {
									if(!StringUtils.isEmpty(s)) {
										colums.add(s);
									}
								}
								sendMessage(ResultUtil.getOk(loadStats(colums)));
								if(!flag) {
									log.info("cmd:  docker stats is close");
									throw new IOException("stats no container");
								}
							}
						}else {
							sendMessage(new Result<>("c_stop_0", "容器已停止",false));
							throw new IOException("stats no container");
						}
					}
				});
			}
			
		} catch (IOException e) {
			//log.warn("docker container is error:"+e.getMessage(),e);
		}finally {
			if(in!=null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private void sendMessage(Object msg) throws IOException {
		session.getBasicRemote().sendText(JSON.toJSONString(msg));
	}

	/**
	 * 	将数组内容转换成docker stats 监听数据
	 * @param columns
	 * @return
	 * @author 王帆
	 * @date 2019年11月5日 下午5:06:17
	 */
	public static ContainerStatsBean loadStats(List<String> columns) {
		if(CollectionUtils.isEmpty(columns) || columns.size()!=8) {
			return null;
		}
		ContainerStatsBean stats=new ContainerStatsBean();
		stats.setContainerId(columns.get(0).trim());
		stats.setContainerName(columns.get(1).trim());
		stats.setCupUsing(columns.get(2).trim());
		stats.setMemberCacheUsing(columns.get(3).trim());
		stats.setMemberUsing(columns.get(4).trim());
		stats.setNetIO(columns.get(5).trim());
		stats.setBlockIO(columns.get(6).trim());
		stats.setPID(columns.get(7).trim());
		return stats;
	}

	/**
	 * 	连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		flag=false;
	}

	/**
	 * 	收到客户端消息后调用的方法
	 * @param message 客户端发送过来的消息
	 */
	@OnMessage
	public void onMessage(String message, Session session) {

	}

	/**
	 *	 发生错误时调用
	 */
	@OnError
	public void onError(Session session, Throwable error) {
	}

}
