package com.sky.container.socket.server;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import com.sky.container.socket.stream.SocketSessionPrintWriter;
import lombok.Data;

/**
 * shell  服务控制
 * @author 王帆
 * @date  2019年10月30日 上午8:53:28
 */
@Data
public class ShellService {
	private String host;
	private Integer port;
	private String user;
	private String password;
	private ShellUser shelluser;
	private javax.websocket.Session socketSessin;
	private Session session;
	private ChannelShell shell;
	private OutputStream out;
	
	public ShellService(String host,int port,String user,String  passwd,javax.websocket.Session socketSessin) {
		this.host=host;
		this.port=port;
		this.user=user;
		this.password=passwd;
		this.socketSessin=socketSessin;
		this.setShelluser(new ShellUser(passwd));
	}

	public void run() throws JSchException {
		JSch jsch=new JSch();
		session=jsch.getSession(user, host, port);
		session.setUserInfo(this.shelluser);
		session.connect();
		Channel channel=session.openChannel("shell");
		shell = (ChannelShell)channel;
		//设置命令操作流程  输出流
		shell.setOutputStream(new SocketSessionPrintWriter(this.socketSessin));
		try {
			out=shell.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		shell.connect();
	}

	public static class ShellUser implements UserInfo{
		private String passwd;
		public ShellUser(String passwd) {
			this.passwd=passwd;
		}
		@Override
		public String getPassphrase() {return null;	}

		@Override
		public String getPassword() {return passwd;}

		@Override
		public boolean promptPassword(String message) {return true;}

		@Override
		public boolean promptPassphrase(String message) {return true;}

		@Override
		public boolean promptYesNo(String message) {return true;}

		@Override
		public void showMessage(String message) {}

	}

	private static List<Integer> conds=Arrays.asList(9,13);
	/**
	 * 	推送数据流程
	 * @param message
	 * @author 王帆
	 * @date 2019年10月30日 上午9:09:35
	 */
	public void put(String message) {
		if(out!=null) {
			try {
				byte[] buf = message.getBytes();
				out.write(buf);
				if(isFlush(buf)) {
					out.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean isFlush(byte buf[]) {
		for(int i=0;i<buf.length;i++) {
			if(conds.contains((int)buf[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 	关闭数据链接
	 * 
	 * @author 王帆
	 * @date 2019年10月30日 上午9:09:19
	 */
	public void close() {
		if(shell.isConnected()) {
			shell.disconnect();
		}
		if(session.isConnected()) {
			session.disconnect();
		}
	}

}
