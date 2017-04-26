package sever;

import java.awt.event.MouseWheelEvent;
import java.nio.charset.Charset;

import bean.ClientBean;
import decice.ControlDevice;
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
		System.out.println("-------------Server------Recive------------------");
		ClientBean rec = (ClientBean) msg;
		if (rec.name != null) {
			if (rec.connectcmd != null) {
				switch (rec.connectcmd) {
				case ConnectServer:
					System.out.println("client:" + ctx.channel().remoteAddress() + ": connect");
					switch (rec.myType) {
					case Controler:
						ControlDevicesManager.getInstance().addControlDevice(ctx, rec.DeviceID);
						break;
					case ExCutor:
						MeaDevicesManager.getInstance().addControlDevice(ctx, rec.name);
						break;
					default:
						break;
					}
					break;
				case SendData:
					System.out.println("client:" + ctx.channel().remoteAddress() + rec.DeviceID + ": send data "
							+ rec.remoteDeviceID);
					ControlDevice controlDevice1 = ControlDevicesManager.getInstance()
							.getControlDevice(rec.remoteDeviceID);
					if (controlDevice1 != null) {
						controlDevice1.sendCmd("data from " + rec.DeviceID+" : "+rec.data);
					} else {
						ControlDevicesManager.getInstance().getControlDevice(rec.DeviceID).sendCmd("no Device fund!");
					}
					break;
				case SendMeaCmd:
					System.out.println("client:" + ctx.channel().remoteAddress() + rec.DeviceID + ": sendMeaCmd"
							+ rec.remoteDeviceID);
					ControlDevice controlDevice = ControlDevicesManager.getInstance()
							.getControlDevice(rec.remoteDeviceID);
					if (controlDevice != null) {
						controlDevice.sendCmd("meaCmd from " + rec.DeviceID);
					} else {
						ControlDevicesManager.getInstance().getControlDevice(rec.DeviceID).sendCmd("no Device fund!");
					}
					break;
				case HeartPackage:
					System.out.println(
							"client:" + ctx.channel().remoteAddress() + ": heart Package from DeviceID:" + rec.name);
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
		System.out.println("clinet:" + ctx.channel().remoteAddress().toString() + "  leave");
		ControlDevicesManager.getInstance().removeControlDevice(ctx.channel().remoteAddress());
		ChannelFuture future = ctx.close();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
	}

}
