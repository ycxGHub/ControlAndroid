package decice;

import bean.ServerBean;
import io.netty.channel.ChannelHandlerContext;

public class ControlDevice {
	private long DeviceId;
	private ChannelHandlerContext context;
	private boolean isAlive=true;
	public boolean isAlive() {
		return isAlive;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	public ControlDevice(long deviceId, ChannelHandlerContext channel) {
		// TODO Auto-generated constructor stub
		this.DeviceId = deviceId;
		this.context = channel;
	}
	public void sendCmd(String servercmd)
	{
		System.out.println("sendCmd to"+DeviceId+servercmd);
		ServerBean serverBean=new ServerBean();
		serverBean.setData(servercmd);
		context.writeAndFlush(serverBean);
	}
	public long getDeviceId() {
		return DeviceId;
	}
	public void setDeviceId(long deviceId) {
		DeviceId = deviceId;
	}
	public ChannelHandlerContext getContext() {
		return context;
	}
	public void setContext(ChannelHandlerContext context) {
		this.context = context;
	}
}
