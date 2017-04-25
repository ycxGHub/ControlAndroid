package decice;

import bean.ServerBean;
import io.netty.channel.ChannelHandlerContext;

public class ControlDevice {
	private String DeviceId;
	private ChannelHandlerContext context;

	public ControlDevice(String deviceId, ChannelHandlerContext channel) {
		// TODO Auto-generated constructor stub
		this.DeviceId = deviceId;
		this.context = channel;
	}
	public void sendCmd(String servercmd)
	{
		ServerBean serverBean=new ServerBean();
		serverBean.setData(servercmd);
		context.writeAndFlush(serverBean);
	}
	public String getDeviceId() {
		return DeviceId;
	}
	public void setDeviceId(String deviceId) {
		DeviceId = deviceId;
	}
	public ChannelHandlerContext getContext() {
		return context;
	}
	public void setContext(ChannelHandlerContext context) {
		this.context = context;
	}
}
