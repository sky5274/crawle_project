package com.sky.container.socket.stream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import javax.websocket.Session;

/**
 * 	socket session outputStream
 * @author 王帆
 * @date  2019年10月29日 下午5:13:02
 */
public class SocketSessionPrintWriter extends OutputStream{
	private Session session;
	private byte[] bytes=new byte[32];
	private int count=0;
	
	public SocketSessionPrintWriter(Session session) {
		this.session=session;
	}

	@Override
	public void write(int b) throws IOException {
		if(count>bytes.length*0.75) {
			grow();
		}
		bytes[count]=(byte) b;
		count++;
	}
	private void grow() {
		bytes=Arrays.copyOf(bytes, bytes.length*2);
	}
	
	private void initByte(int size) {
		bytes=new byte[size];
	}

	public void write(byte b[], int off, int len) throws IOException {
		if (b == null) {
			throw new NullPointerException();
		} else if ((off < 0) || (off > b.length) || (len < 0) ||
				((off + len) > b.length) || ((off + len) < 0)) {
			throw new IndexOutOfBoundsException();
		} else if (len == 0) {
			return;
		}
		for (int i = 0 ; i < len ; i++) {
			write(b[off + i]);
		}
	}

	@Override
	public void flush() throws IOException {
		super.flush();
		session.getBasicRemote().sendText(new String(bytes));
		initByte(32);
		count=0;
	}
}
