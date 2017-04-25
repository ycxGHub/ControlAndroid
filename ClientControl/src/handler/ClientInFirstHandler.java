package handler;

import bean.ClientBean;
import bean.ServerBean;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.nio.AbstractNioChannel.NioUnsafe;
import service.TimeClient;
import service.iml.INotifyServer;
import util.CmdManager.ConnectCmd;

public class ClientInFirstHandler extends ChannelInboundHandlerAdapter {
	
	public  INotifyServer iNotifyServer;
	public ClientInFirstHandler(INotifyServer listener) {
		iNotifyServer=listener;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) { // (1)
		ChannelManager.writeToServerChannel=ctx;
		iNotifyServer.notifyClientConnectState(true);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
		ChannelManager.writeToServerChannel=null;
		TimeClient.isOnlogin = false;
		iNotifyServer.notifyClientConnectState(false);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		ServerBean serverBean = (ServerBean) msg;
		iNotifyServer.notifyClientData(serverBean.getData());
	}
}
