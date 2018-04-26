package state2.rmisever;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.Map;

public class RMIHelper {
	
	public static void startRMIService(Map<String, Remote> bindingMap) {
		
		for(String serviceName : bindingMap.keySet()) {
			
			Remote remote = bindingMap.get(serviceName);
			
			/**通过Runtime调用命令行，执行RMI相关命令*/
			try {
				String command = null;
				
				// 切换到类路径根目录
				String pathOfBin = System.getProperty("user.dir").replaceFirst("/", "") + "/target/classes";
				command = "cmd.exe /C cd " + pathOfBin;
				System.out.println(command);
				
				// 执行rmic命令，生成STUB桩
				String packageName = remote.getClass().getPackage().getName();
				command = command + " && " + "rmic "+packageName+"."+remote.getClass().getSimpleName();
				System.out.println(command);
				Object rmicResult = exec(command)[1];
				System.out.println("rmicResult="+rmicResult);
				
				// 启动rmiregistry
				command = "cmd.exe /C cd " + pathOfBin;
				command += " && rmiregistry";
				System.out.println("registry="+command);
				Object registryResult = exec(command)[1];
				System.out.println("registryResult1="+registryResult);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			/**绑定服务*/
			try {
				Naming.rebind(serviceName, remote);
				System.out.println("绑定成功");
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			/**查询绑定成功的服务*/
			try {
				Registry registry = LocateRegistry.getRegistry();
				System.out.println(Arrays.toString(registry.list()));
			} catch (AccessException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private static Object[] exec(String command) {
		try {
			Process p = Runtime.getRuntime().exec(command);
			String executeResult = 
				new BufferedReader(
						new InputStreamReader(
								p.getInputStream(), "GBK")).readLine();
			return new Object[] {p, executeResult};
		} catch (IOException e) {
			throw new RuntimeException();
		}
		
	}
}
