package state2.state;

import java.util.Random;

import state2.rmisever.GumballMachine;

/**
 * 当前状态：投入了硬币
 *
 */
public class HasQuarterState extends State {
	
	private static final long serialVersionUID = 1230912393042454749L;

	Random randWinner = new Random(System.currentTimeMillis());
	
	// transient 不序列化该字段-RMI远程方法调用返回结果时，不对该字段进行序列号
	transient GumballMachine gumballMachine;
	
	public HasQuarterState(GumballMachine gumballMachine) {
		this.gumballMachine = gumballMachine;
	}

	/**
	 * 取消购买，退回硬币
	 */
	@Override
	public void ejectQuarter() {
		System.out.println("退回硬币，请查收");
		gumballMachine.setState(gumballMachine.getNoQuarterState());
	}

	/**
	 * 确定购买，转动曲柄---投入硬币后，该状态下进行随机抽奖
	 */
	@Override
	public void turnCrank() {
		System.out.println("你转动了曲柄");
		int winner = randWinner.nextInt(10);
		if(winner == 8 && gumballMachine.getCount() > 1) {
			gumballMachine.setState(gumballMachine.getWinnerSoldState());
		} else {
			gumballMachine.setState(gumballMachine.getSoldState());
		}
	}

}
