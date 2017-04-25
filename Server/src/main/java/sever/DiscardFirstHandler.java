package sever;

import java.awt.event.MouseWheelEvent;
import java.nio.charset.Charset;

import bean.ClientBean;
import decice.ControlDevicesManager;
import decice.MeaDevicesManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import util.CmdManager;

public class DiscardFirstHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Server------Recive");
		ClientBean rec = (ClientBean) msg;
		if (rec.name != null) {
			System.out.println(rec.name);
			if (rec.connectcmd != null) {
				switch (rec.connectcmd) {
				case ConnectServer:
					System.out.println("client:" + ctx.channel().remoteAddress() + ": connect");
					switch (rec.myType) {
					case Controler:
						ControlDevicesManager.getInstance().addControlDevice(ctx, rec.name);
						break;
					case ExCutor:
						MeaDevicesManager.getInstance().addControlDevice(ctx, rec.name);
						break;
					default:
						break;
					}
					break;
				case SendData:
					System.out.println("client:" + ctx.channel().remoteAddress() + ": sendData");
					break;
				case SendMeaCmd:
					System.out.println("client:" + ctx.channel().remoteAddress() + ": sendMeaCmd");
					break;
				default:
					break;
				}
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		System.out.println("clinet:" + ctx.channel().remoteAddress().toString() + "  leave");
		ctx.close();
	}

}
