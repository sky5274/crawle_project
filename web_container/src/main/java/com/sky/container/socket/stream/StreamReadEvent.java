package com.sky.container.socket.stream;

import java.io.IOException;

/**
 * 	数据流执行事件
 * @author 王帆
 * @date  2019年11月5日 下午3:08:19
 */
public interface StreamReadEvent {
	
	void invoke(String str) throws IOException;
}
