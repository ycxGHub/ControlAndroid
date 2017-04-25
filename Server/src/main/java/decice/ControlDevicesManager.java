package decice;

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
		if (scontrolDevicesManager != null) {
			scontrolDevicesManager = new ControlDevicesManager();
		}
		return scontrolDevicesManager;
	}

	public void addControlDevice(ChannelHandlerContext channelHandlerContext, String currentId) {
		for (ControlDevice controlDevice2 : controlDevices) {
			if (controlDevice2.getDeviceId().equals(currentId)) {
				return;
			}
		}
		ControlDevice controlDevice = new ControlDevice(currentId, channelHandlerContext);
		controlDevices.add(controlDevice);
	}

	public void removeControlDevice(ControlDevice controlDevice) {
		int index = -1;
		for (ControlDevice controlDevice2 : controlDevices) {
			if (controlDevice2.getDeviceId() .equals( controlDevice.getDeviceId())) {
				index = controlDevices.indexOf(controlDevice2);
			}
		}
		if (index >= 0) {
			controlDevices.remove(index);
		}
	}

	public ControlDevice getControlDevice(String currentId) {
		for (ControlDevice controlDevice2 : controlDevices) {
			if (controlDevice2.getDeviceId() .equals( currentId)) {
				return controlDevice2;
			}
		}
		return null;
	}

}
