package state2;

import state2.rmisever.GumballMachine;

public class GumballMachineLocalTestDriver {
	public static void main(String[] args) throws Exception {
		
		GumballMachine gumballMachine = new GumballMachine("Seattle", 5); // 西雅图
		GumballLocalMonitor gumballMonitor = new GumballLocalMonitor(gumballMachine);
		
		System.out.println(gumballMachine);
		gumballMonitor.report();
		
		gumballMachine.insertQuarter();
		gumballMachine.turnCrank();
		
		System.out.println(gumballMachine);
		
		gumballMachine.insertQuarter();
		gumballMachine.turnCrank();
		gumballMachine.insertQuarter();
		gumballMachine.turnCrank();
		gumballMachine.insertQuarter();
		gumballMachine.turnCrank();
		System.out.println(gumballMachine);
		gumballMonitor.report();
		
		// 装入新的糖果
		gumballMachine.refill(8);
		System.out.println(gumballMachine);
		gumballMonitor.report();
		
		// 未投币的情况下，退回硬币-抛异常
		gumballMachine.ejectQuarter();
	}
}
