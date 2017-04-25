package service.iml;

public interface INotifyServer {
 public void notifyClientData(String data);
 public void notifyClientConnectState(boolean state);
}
