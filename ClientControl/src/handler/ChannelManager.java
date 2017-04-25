package handler;

import java.util.jar.Attributes.Name;

import bean.ClientBean;
import bean.ClientBean.ClientBeanType;
import io.netty.channel.ChannelHandlerContext;
import util.CmdManager;
import util.CmdManager.ConnectCmd;
import util.CmdManager.MeaCmd;

public class ChannelManager {
	static ChannelHandlerContext writeToServerChannel;

	public static void sendCmd(ConnectCmd cmd,MeaCmd cmd2)
	{
		ClientBean clientBean=new ClientBean();
		clientBean.name=writeToServerChannel.channel().localAddress().toString();
		clientBean.connectcmd=cmd;
		clientBean.meaCmd=cmd2;
		clientBean.myType=ClientBeanType.Controler;
		writeToServerChannel.write(clientBean);
		writeToServerChannel.flush();
	}
	public static void sendCmd(String name)
	{
		ClientBean clientBean=new ClientBean();
		clientBean.name=name+"";
		clientBean.myType=ClientBeanType.Controler;
		clientBean.connectcmd=ConnectCmd.SendData;
		writeToServerChannel.write(clientBean);
		writeToServerChannel.flush();
	}
	public static String getServerAddress()
	{
		return writeToServerChannel.channel().remoteAddress().toString();
	}
}
