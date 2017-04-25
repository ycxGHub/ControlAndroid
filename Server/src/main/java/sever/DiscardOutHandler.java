package sever;

import java.net.SocketAddress;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.ssl.SslContext;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class DiscardOutHandler extends ChannelOutboundHandlerAdapter {
	@Override
	public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		super.bind(ctx, localAddress, promise);
	}

	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress,
			ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		super.connect(ctx, remoteAddress, localAddress, promise);
	}

	@Override
	public void read(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.read(ctx);
	}

	@Override
	public void write(final ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		  ByteBuf res=ctx.alloc().buffer(1024);
		  ByteBuf msg1=(ByteBuf) msg;
		  String msg2=msg1.toString(CharsetUtil.UTF_8);
		   res.writeBytes((msg2+"gg").getBytes());
		  ChannelFuture ch= ctx.writeAndFlush(res);
		  ch.addListener(new ChannelFutureListener() {
			
			public void operationComplete(ChannelFuture future) throws Exception {
				// TODO Auto-generated method stub
				if (future.isSuccess()) {
					ctx.close();
				}
			}
		} );
		super.write(ctx, msg, promise);
		
	}
	

}
