package com.sky.rpc.provider.socket;

import java.io.IOException;
import java.net.ServerSocket;

import com.sky.rpc.provider.ProviderServer;


public class ProviderSocketServer extends ProviderServer{

	private ServerSocket server;
	public ServerSocket getServer() {
		return server;
	}
	public void setServer(ServerSocket server) {
		this.server = server;
	}
	
	public void run() {
		if(ProviderServer.isOpen()) {
			return;
		}
		try {
			isOpen=true;
			server=new ServerSocket(getPort());
			log.info("rpc socket provider start in port: "+getPort());
			while(isOpen) {
				ProviderSocketServerTask provideServiceTask =new ProviderSocketServerTask();
				 executor.execute(provideServiceTask.append(server.accept()));
			}
		} catch (IOException e) {
			log.error("rpc provirder runing error",e);
		}
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void close() {
		try {
			server.close();
			isOpen=false;
		} catch (IOException e) {
			log.error("rpc privoder close error");
		}finally {
			server=null;
		}
		stop();
	}
}
