package state2.rmiclient;

import java.rmi.Naming;

import state2.rmisever.GumballMachineRemote;

/**
 * 远程客户端通过RMI的Naming.lookup查询指定名称的服务
 *	将给服务对象传递给Monitor，输出report报告
 *
 * RMI-底层通过socket向服务端发起请求，取回结果。
 */
public class GumaballMonitorClient {
	public static void main(String[] args) {
		
		String[] locations = {"rmi://127.0.0.1/Santafe",
							"rmi://127.0.0.1/Boulder",
							"rmi://127.0.0.1/Seattle"};
		// 一组监视器对象
		GumballMonitor[] monitors = new GumballMonitor[locations.length];
		
		for(int i=0; i<locations.length; i++) {
			try {
				GumballMachineRemote machine = (GumballMachineRemote) Naming.lookup(locations[i]);
				monitors[i] = new GumballMonitor(machine);
				monitors[i].report();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
