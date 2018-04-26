package state2.rmiclient;

import java.rmi.RemoteException;

import state2.rmisever.GumballMachineRemote;

/**
 * 监视器对象-通过服务器定义的远程服务接口，输出报告。
 */
public class GumballMonitor {
	
	GumballMachineRemote machine; // 服务端开放的远程服务接口

	public GumballMonitor(GumballMachineRemote machine) {
		this.machine = machine;
	}
	
	public void report() {
		try {
			System.out.println("\n"+this);
			System.out.println("Gumball Machine: " + machine.getLocation());
			System.out.println("Current State: " + machine.getState().getClass().getSimpleName());
			System.out.println("Current Inventory: " + machine.getCount());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
}
