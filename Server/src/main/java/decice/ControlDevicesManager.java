package decice;

import java.net.SocketAddress;
import java.util.ArrayList;

import bean.ClientBean;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.AsciiHeadersEncoder.NewlineType;

public class ControlDevicesManager {
	private ArrayList<ControlDevice> controlDevices = new ArrayList<ControlDevice>();
	private static ControlDevicesManager scontrolDevicesManager;

	private ControlDevicesManager() {

	}

	public static ControlDevicesManager getInstance() {
		if (scontrolDevicesManager == null) {
			scontrolDevicesManager = new ControlDevicesManager();
		}
		return scontrolDevicesManager;
	}

	public void addControlDevice(ChannelHandlerContext channelHandlerContext, long currentId) {
		for (ControlDevice controlDevice2 : controlDevices) {
			if (controlDevice2.getDeviceId() == (currentId)) {
				return;
			}
		}
		ControlDevice controlDevice = new ControlDevice(currentId, channelHandlerContext);
		controlDevice.setAlive(true);
		controlDevices.add(controlDevice);
		for (ControlDevice controlDevice2 : controlDevices) {
			System.out.println("current onLine Device: " + controlDevice2.getDeviceId());
		}
	}

	public void removeControlDevice(SocketAddress  socketAddress) {
		int index = -1;
		for (ControlDevice controlDevice2 : controlDevices) {
			if (controlDevice2.getContext().channel().remoteAddress().equals(socketAddress)) {
				index = controlDevices.indexOf(controlDevice2);
				System.out.println(index + "");
			}
		}
		if (index >= 0) {
			controlDevices.remove(index);
		}
	}

	public ControlDevice getControlDevice(long currentId) {
		System.out.println(currentId + "");
		for (ControlDevice controlDevice2 : controlDevices) {
			if (controlDevice2.getDeviceId() == currentId) {
				return controlDevice2;
			}
		}
		return null;
	}

	public void checkDeviceIsActive() {
	}

}
