package remote.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {

	private static final long serialVersionUID = -7340687517813886920L;

	protected MyRemoteImpl() throws RemoteException {
		super();
	}

	@Override
	public String sayHello() { 
		System.out.println("服务器接收消息成功");
		return "服务器：你好，客户端！";
	}
	
}