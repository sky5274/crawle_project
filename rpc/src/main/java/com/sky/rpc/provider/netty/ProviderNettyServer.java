package com.sky.rpc.provider.netty;



import com.sky.rpc.provider.ProviderServer;
import com.sky.rpc.provider.socket.ProviderSocketServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;


/**
 * rpc  netty server
 * @author 王帆
 * @date  2019年6月13日 下午12:42:50
 */
public class ProviderNettyServer extends ProviderServer{
	private static final EventLoopGroup workerGroup = new NioEventLoopGroup(10);
	private static final EventLoopGroup group = new NioEventLoopGroup(10);
	ServerBootstrap bootstrap = new ServerBootstrap();

	public void run() {
		if(isOpen() && canOpen()) {
			ProviderSocketServer.setOpen(true);
			return;
		}
		setOpen(true);
		bootstrap.group(workerGroup,group).
		channel(NioServerSocketChannel.class).
		option(ChannelOption.SO_BACKLOG,1024).
		childOption(ChannelOption.SO_KEEPALIVE,true).
		childOption(ChannelOption.TCP_NODELAY,true).
		childHandler(new ChannelInitializer<SocketChannel>() {
			//创建NIOSocketChannel成功后，在进行初始化时，将它的ChannelHandler设置到ChannelPipeline中，用于处理网络IO事件
			protected void initChannel(SocketChannel channel) throws Exception {
				ChannelPipeline pipeline = channel.pipeline();
				//pipeline.addLast(new IdleStateHandler(60, 20, 60 * 10,TimeUnit.SECONDS));
				pipeline.addLast(new ObjectEncoder());
				pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE,ClassResolvers.cacheDisabled(null)));
				//pipeline.addLast(applicationContext.getBean(ProviderStringNettyHandel.class));
				pipeline.addLast(new ProviderObjectNettyHandel());
			}
		});
		ChannelFuture cf;
		try {
			cf = bootstrap.bind(ProviderServer.getPort()).sync();
			log.info("RPC netty 服务器启动.监听端口:"+getPort());
			//等待服务端监听端口关闭
			cf.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public void close() {
		stop();
		setOpen(false);
	}

}
