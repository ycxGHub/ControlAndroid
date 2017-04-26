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

	public static void sendConnectCmd(ConnectCmd cmd, String currentId) {
		ClientBean clientBean = new ClientBean();
		clientBean.name = writeToServerChannel.channel().localAddress().toString();
		clientBean.DeviceID = getLongFromString(currentId);
		clientBean.connectcmd = cmd;
		clientBean.myType = ClientBeanType.Controler;
		writeToServerChannel.write(clientBean);
		writeToServerChannel.flush();
	}

	public static void sendMeaCmd(String currentID, String androidID) {
		ClientBean clientBean = new ClientBean();
		clientBean.DeviceID = getLongFromString(currentID);
		clientBean.remoteDeviceID = getLongFromString(androidID);
		clientBean.myType = ClientBeanType.Controler;
		clientBean.connectcmd = ConnectCmd.SendMeaCmd;
		writeToServerChannel.write(clientBean);
		writeToServerChannel.flush();
	}

	public static String getServerAddress() {
		return writeToServerChannel.channel().remoteAddress().toString();
	}

	public static void sendHeartCmd(String name) {
		ClientBean clientBean = new ClientBean();
		clientBean.name = name + "";
		clientBean.myType = ClientBeanType.Controler;
		clientBean.connectcmd = ConnectCmd.HeartPackage;
		writeToServerChannel.write(clientBean);
		writeToServerChannel.flush();
	}

	public static void sendMeaCmd(String currentID, String androidID,String data) {
		ClientBean clientBean = new ClientBean();
		clientBean.DeviceID = getLongFromString(currentID);
		clientBean.remoteDeviceID = getLongFromString(androidID);
		clientBean.myType = ClientBeanType.Controler;
		clientBean.connectcmd = ConnectCmd.SendData;
		clientBean.data=data;
		writeToServerChannel.write(clientBean);
		writeToServerChannel.flush();
	}
	public static boolean isEnable() {
		if (writeToServerChannel != null) {
			return true;
		}
		return false;
	}

	private static long getLongFromString(String var) {
		if (var == null) {
			return 0;
		} else if (var.length() == 0) {
			return 0;
		}
		else{
			return Long.parseLong(var);
		}
	}
}
