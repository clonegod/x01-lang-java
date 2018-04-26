package clonegod.rpc.rmi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import clonegod.rpc.rmi.api.ISayHello;

public class Client {
	public static void main(String[] args) {
		
		try {
			// 从注册中心获取服务代理对象
			Remote remote = Naming.lookup("rmi://127.0.0.1:9000/sayHello");
			System.out.println(remote);
			
			ISayHello remoteSerive = (ISayHello) remote;
			
			String ret = remoteSerive.sayHello("菲菲");
			
			System.out.println(Thread.currentThread().getName() + ": " + ret); 
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
	}
}
