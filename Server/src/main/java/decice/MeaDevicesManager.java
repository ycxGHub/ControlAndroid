package decice;

import java.util.ArrayList;

import io.netty.channel.ChannelHandlerContext;

public class MeaDevicesManager {
	private ArrayList<MeaDevice> controlDevices = new ArrayList<MeaDevice>();
	private static MeaDevicesManager scontrolDevicesManager;

	private MeaDevicesManager() {
		// TODO Auto-generated constructor stub
	}
	public static MeaDevicesManager getInstance() {
		if (scontrolDevicesManager != null) {
			scontrolDevicesManager = new MeaDevicesManager();
		}
		return scontrolDevicesManager;
	}

	public void addControlDevice(ChannelHandlerContext channelHandlerContext, String currentId) {
		for (MeaDevice controlDevice2 : controlDevices) {
			if (controlDevice2.getDeviceId().equals(currentId)) {
				return;
			}
		}
		MeaDevice controlDevice = new MeaDevice(currentId, channelHandlerContext);
		controlDevices.add(controlDevice);
	}

	public void removeControlDevice(MeaDevice controlDevice) {
		int index = -1;
		for (MeaDevice controlDevice2 : controlDevices) {
			if (controlDevice2.getDeviceId() .equals( controlDevice.getDeviceId())) {
				index = controlDevices.indexOf(controlDevice2);
			}
		}
		if (index >= 0) {
			controlDevices.remove(index);
		}
	}

	public MeaDevice getControlDevice(String currentId) {
		for (MeaDevice controlDevice2 : controlDevices) {
			if (controlDevice2.getDeviceId() .equals( currentId)) {
				return controlDevice2;
			}
		}
		return null;
	}
}
