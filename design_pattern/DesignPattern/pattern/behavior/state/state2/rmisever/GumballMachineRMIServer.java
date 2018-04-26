package state2.rmisever;

import java.rmi.Remote;
import java.util.HashMap;
import java.util.Map;

/**
 * 启动rmiregistry，向其中注册RMI服务。
 * 
 */
public class GumballMachineRMIServer {
	
	public static void main(String[] args) throws Exception {
		
		String[][] locations = {
				{"Santafe", "5"},
				{"Boulder", "6"},
				{"Seattle", "8"}
		};
		
		Map<String, Remote> bindingMap = new HashMap<>();
		
		for(int i=0; i<locations.length; i++) {
			GumballMachineRemote gumballMachine = new GumballMachine(
					locations[i][0], Integer.parseInt(locations[i][1]));
			bindingMap.put(locations[i][0], gumballMachine);
		}
		
		RMIHelper.startRMIService(bindingMap);
		
	}
	
}
