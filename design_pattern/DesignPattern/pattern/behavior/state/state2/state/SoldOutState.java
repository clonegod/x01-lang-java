package state2.state;

import state2.rmisever.GumballMachine;

/**
 * 当前状态：糖果售罄
 *
 */
public class SoldOutState extends State {

	private static final long serialVersionUID = -6183359062605478328L;
	
	// transient 不序列化该字段-RMI远程方法调用返回结果时，不对该字段进行序列号
	transient GumballMachine gumballMachine;
	
	public SoldOutState(GumballMachine gumballMachine) {
		this.gumballMachine = gumballMachine;
	}

}
