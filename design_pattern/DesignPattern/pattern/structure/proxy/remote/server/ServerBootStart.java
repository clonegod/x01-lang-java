package remote.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class ServerBootStart {
	
	public static void main(String[] args) throws RemoteException {
		Map<String, Remote> map = new HashMap<>();

		MyRemote service = new MyRemoteImpl();
		
		map.put("RemoteHelloService", service);
		
		RMIHelper.startRMIService(map);
	}
		
}
