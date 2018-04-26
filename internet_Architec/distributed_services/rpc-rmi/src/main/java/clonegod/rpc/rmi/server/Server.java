package clonegod.rpc.rmi.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import clonegod.rpc.rmi.api.ISayHello;

public class Server {
	
	private static final String name = "rmi://127.0.0.1:9000/sayHello";
	
	public static void main(String[] args) {
		try {
			ISayHello helloService = new SyaHelloImpl();
			

			// 启动注册服务 
			LocateRegistry.createRegistry(9000);
			
			// 向注册服务绑定服务对象
			Naming.bind(name, helloService);
			
			System.err.println(Thread.currentThread().getName() + ": Server start success");
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		} 
		
		System.out.println("end");
	}
	
}
