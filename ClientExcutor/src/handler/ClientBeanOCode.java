package handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class ClientBeanOCode extends ChannelOutboundHandlerAdapter {

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
//		ClientBean clientBean = (ClientBean) msg;
//		ctx.writeAndFlush(clientBean);
//		ByteBuf byteBuf = ctx.alloc().buffer(1024);
//		byteBuf.writeBytes(clientBean.name.toString().getBytes());
//		System.out.println(clientBean.name);
//		ChannelFuture f = ctx.writeAndFlush(byteBuf);
//		super.write(ctx, msg, promise);
	
	}

}
