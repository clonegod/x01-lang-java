package remote.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import remote.server.MyRemote;

public class MyRemoteClient {
	public static void main(String[] args) {
		new MyRemoteClient().go();
	}

	private void go() {
		try {
			MyRemote service = 
					(MyRemote) Naming.lookup("rmi://127.0.0.1/RemoteHelloService");
			String s = service.sayHello();
			System.out.println(s);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
	}
}
