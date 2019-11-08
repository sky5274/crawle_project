package com.sky.test;

import java.io.IOException;
import javax.swing.JOptionPane;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import com.sky.test.JavaSftpTest.MyUserInfo;

/**
 * java open ssh test demo
 * @author 王帆
 * @date  2019年11月8日 下午4:10:26
 */
public class JavaOpensshTest {
	public static void main(String[] arg) throws JSchException, IOException {
		JSch jsch=new JSch();

		String host=null;
		if(arg.length>0){
			host=arg[0];
		}
		else{
			host=JOptionPane.showInputDialog("Enter username@hostname",
					System.getProperty("user.name")+
					"@127.0.0.1"); 
		}
		String user=host.substring(0, host.indexOf('@'));
		host=host.substring(host.indexOf('@')+1);
		int port=22;
		
		Session session=jsch.getSession(user, host, port);

		// username and password will be given via UserInfo interface.
		UserInfo ui=new MyUserInfo();
		session.setUserInfo(ui);

		session.connect();

		Channel channel=session.openChannel("shell");
		ChannelShell shell = (ChannelShell)channel;
		
		shell.setInputStream(System.in);
	      /*
	      // a hack for MS-DOS prompt on Windows.
	      channel.setInputStream(new FilterInputStream(System.in){
	          public int read(byte[] b, int off, int len)throws IOException{
	            return in.read(b, off, (len>1024?1024:len));
	          }
	        });
	       */

	      shell.setOutputStream(System.out);
	      shell.connect(3000*2);
	}
}
