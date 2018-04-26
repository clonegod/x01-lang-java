package state2.state;

import state2.rmisever.GumballMachine;

/**
 * 当前状态：正常售出糖果
 */
public class SoldState extends State {
	
	private static final long serialVersionUID = -4270913649936352583L;
	
	// transient 不序列化该字段-RMI远程方法调用返回结果时，不对该字段进行序列号
	transient GumballMachine gumballMachine;
	
	public SoldState(GumballMachine gumballMachine) {
		this.gumballMachine = gumballMachine;
	}

	/**
	 * 释放1颗糖果
	 */
	@Override
	public void dispense() {
		gumballMachine.releaseBall();
		if(gumballMachine.getCount() > 0) {
			gumballMachine.setState(gumballMachine.getNoQuarterState());
		} else {
			System.out.println("Oops, 糖果售罄");
			gumballMachine.setState(gumballMachine.getSoldOutState());
		}
	}

}
