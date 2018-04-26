package state2.state;

import state2.rmisever.GumballMachine;

/**
 * 当前状态：中奖-免费领取1颗糖果
 *
 */
public class WinnerSoldState extends State {
	
	private static final long serialVersionUID = -8783476545224868018L;
	
	// transient 不序列化该字段-RMI远程方法调用返回结果时，不对该字段进行序列号
	transient GumballMachine gumballMachine;
	
	public WinnerSoldState(GumballMachine gumballMachine) {
		this.gumballMachine = gumballMachine;
	}

	/**
	 * 释放2颗糖果
	 */
	@Override
	public void dispense() {
		System.out.println("恭喜你中奖了。本次你将获得2颗糖果。");
		gumballMachine.releaseBall();
		if(gumballMachine.getCount() == 0) {
			gumballMachine.setState(gumballMachine.getSoldOutState());
		} else {
			// 额外赠送1颗糖果
			gumballMachine.releaseBall();
			if(gumballMachine.getCount() > 0) {
				gumballMachine.setState(gumballMachine.getNoQuarterState());
			} else {
				System.out.println("Oops, 糖果售罄");
				gumballMachine.setState(gumballMachine.getSoldOutState());
			}
		}
	}

}
