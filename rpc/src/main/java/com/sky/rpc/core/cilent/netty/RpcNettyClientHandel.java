package com.sky.rpc.core.cilent.netty;

import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.rpc.base.Result;
import com.sky.rpc.base.RpcRequest;
import com.sky.rpc.core.cilent.RpcClientHandel;
import com.sky.rpc.core.cilent.netty.NettyClientMessageHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class RpcNettyClientHandel implements RpcClientHandel{
	Log logger = LogFactory.getLog(this.getClass());
	private  EventLoopGroup group = new NioEventLoopGroup(2);
	private  Bootstrap bootstrap ;
	NettyClientMessageHandler clientHandler =new NettyClientMessageHandler();

	public static Map<Long, Channel> channelMap=new ConcurrentHashMap<>();

	public static Map<Long, Channel> getChannelMap() {
		return channelMap;
	}

	public static void setChannelMap(Map<Long, Channel> channelMap) {
		RpcNettyClientHandel.channelMap = channelMap;
	}


	public void initBoot(final int timeout) {
		bootstrap= new Bootstrap();
		bootstrap
		.group(group)
		.channel(NioSocketChannel.class)
		.option(ChannelOption.TCP_NODELAY, true)
		.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout*1000)
		.handler(new ChannelInitializer<SocketChannel>() {
			//创建NIOSocketChannel成功后，在进行初始化时，将它的ChannelHandler设置到ChannelPipeline中，用于处理网络IO事件
			protected void initChannel(SocketChannel channel) throws Exception {
				ChannelPipeline pipeline = channel.pipeline();
				pipeline.addLast(new IdleStateHandler(0, 0, timeout));
				pipeline.addLast(new ObjectEncoder());
				pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE,ClassResolvers.cacheDisabled(null)));
				pipeline.addLast(clientHandler);
			}
		});
	}


	public Channel doConnect(InetSocketAddress address,int timout) throws InterruptedException {
		if(bootstrap==null) {
			initBoot(timout);
			//    		bootstrap.remoteAddress(address);
		}

		long nowtime = System.currentTimeMillis();
		clearChannel(nowtime);

		ChannelFuture future = bootstrap.connect(address);
		Channel channel = future.sync().channel();
		channelMap.put(nowtime, channel);
		return channel;
	}

	private void clearChannel(long nowtime) {
		List<Long> list=new LinkedList<>();
		list.addAll(channelMap.keySet());
		for(Long time:list) {
			if((nowtime-time)>60*1000) {
				Channel channel = channelMap.get(time);
				channel.close();
				channelMap.remove(time);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T invoke(RpcRequest request, InetSocketAddress addr,int timeout) throws Throwable {
		Channel channel = doConnect(addr,timeout);
		if (channel!=null && channel.isActive()) {
			SynchronousQueue<Result<?>> queue =clientHandler.sendRequest(request,channel,timeout);
			Result<?> result = queue.take();
			if(result.hasException()) {
				throw result.getException();
			}else {
				return (T)result.getData();
			}
		}else {
			throw new Exception("netty client is null or not active");
		}
	}
}