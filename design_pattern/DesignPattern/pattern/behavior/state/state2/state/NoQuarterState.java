package state2.state;

import state2.rmisever.GumballMachine;

/**
 * 当前状态：没有硬币
 *
 */
public class NoQuarterState extends State {
	
	private static final long serialVersionUID = -7300640048718245050L;
	
	// transient 不序列化该字段-RMI远程方法调用返回结果时，不对该字段进行序列号
	transient GumballMachine gumballMachine;
	
	public NoQuarterState(GumballMachine gumballMachine) {
		this.gumballMachine = gumballMachine;
	}

	/**
	 * 投入硬币
	 */
	@Override
	public void insertQuarter() {
		System.out.println("\n你投入了一个硬币");
		gumballMachine.setState(gumballMachine.getHasQuarterState());
	}

}
