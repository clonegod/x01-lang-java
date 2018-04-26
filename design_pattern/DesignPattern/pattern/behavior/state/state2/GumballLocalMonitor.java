package state2;

import state2.rmisever.GumballMachine;

public class GumballLocalMonitor {
	GumballMachine gumballMachine;

	public GumballLocalMonitor(GumballMachine gumballMachine) {
		this.gumballMachine = gumballMachine;
	}
	
	public void report() {
		System.out.println("\nGumball Machine: " + gumballMachine.getLocation());
		System.out.println("Current State: " + gumballMachine.getState().getClass().getSimpleName());
		System.out.println("Current Inventory: " + gumballMachine.getCount());
	}
	
}
