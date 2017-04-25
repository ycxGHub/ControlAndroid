package bean;

import java.io.Serializable;

import util.CmdManager;

public class ClientBean implements Serializable{
	private static final long serialVersionUID = -7335293929249462183L;
	public enum ClientBeanType{
		Controler,ExCutor
	}
	public String name="default";
	public ClientBeanType myType;
	public CmdManager.ConnectCmd connectcmd;
	public CmdManager.MeaCmd meaCmd;
}
