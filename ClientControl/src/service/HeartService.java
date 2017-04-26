package service;

import handler.ChannelManager;

public class HeartService {

	public void startConnectServiceOnTime(long time, String name) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (ChannelManager.isEnable()) {
					try {
						Thread.sleep(time);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ChannelManager.sendHeartCmd(name);
				}
			}

		}).start();
	}
}
