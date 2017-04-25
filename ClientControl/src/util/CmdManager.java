package util;

public class CmdManager {
	public enum MeaCmd{
		MeaDis
	}
	public enum ConnectCmd{
		ConnetClient,ConnectServer,UnConnectClient,UnConnectServer,
		SendMeaCmd,SendData,GetData
	}
	public enum DeviceStyle{
		MeaClient,ControlClient,Server
	}
}
