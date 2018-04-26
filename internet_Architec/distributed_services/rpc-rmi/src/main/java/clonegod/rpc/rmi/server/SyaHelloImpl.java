package clonegod.rpc.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import clonegod.rpc.rmi.api.ISayHello;

public class SyaHelloImpl extends UnicastRemoteObject implements ISayHello {

	private static final long serialVersionUID = -7328697563827984436L;

	protected SyaHelloImpl() throws RemoteException {
		super();
	}

	@Override
	public String sayHello(String name) throws RemoteException {
		System.out.println("收到客户端请求，name=" + name);
		return "你好：" + name;
	}

}
