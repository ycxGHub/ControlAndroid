package service;

import handler.ClientInFirstHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import service.iml.INotifyServer;

public class TimeClient {
	public static boolean isOnlogin = false;

	public static void start(INotifyServer iNotifyServer) throws Exception {
		if (!isOnlogin) {
			isOnlogin = true;
			iNotifyServer.notifyClientData("login.....");
			int port = Integer.parseInt("7777");
			EventLoopGroup workerGroup = new NioEventLoopGroup();
			try {
				Bootstrap b = new Bootstrap(); // (1)
				b.group(workerGroup); // (2)
				b.channel(NioSocketChannel.class); // (3)
				b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
				b.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new ObjectEncoder());//±àÂë outÄæÐò
						
						
						
						ch.pipeline().addLast(new ObjectDecoder(1024 * 1024,
								ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));//½âÂë
						ch.pipeline().addLast(new ClientInFirstHandler(iNotifyServer));// inÄæÐò£¬
					}
				});

				// Start the client.
				ChannelFuture f = b.connect("127.0.0.1", port).sync(); // (5)
					
				// Wait until the connection is closed.
				f.channel().closeFuture().sync();
			} finally {
				workerGroup.shutdownGracefully();
			}
		}
	}
}