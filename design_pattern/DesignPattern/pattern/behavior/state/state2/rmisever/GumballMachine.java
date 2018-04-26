package state2.rmisever;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import state2.state.HasQuarterState;
import state2.state.NoQuarterState;
import state2.state.SoldOutState;
import state2.state.SoldState;
import state2.state.State;
import state2.state.WinnerSoldState;

/**
 * Remote服务接口的具体实现类
 *	
 *	通过在运行时动态更新“状态”，实现在不同状态下执行不同的行为
 */
public class GumballMachine
			extends UnicastRemoteObject implements GumballMachineRemote {
	
	private static final long serialVersionUID = -7835330978198123644L;
	
	State soldOutState;		// 状态1：糖果售罄
	State noQuarterState;	// 状态2：没有硬币
	State hasQuarterState;	// 状态3：投入硬币
	State soldState;		// 状态4：售出糖果
	State winnerSoldState;		// 状态4：中奖，赠送1颗糖果
	
	State state = soldOutState; // 初始状态
	int count = 0; // 当前糖果数量
	
	String location;
	
	public GumballMachine(String location, int numberGumballs) throws RemoteException {
		this.location = location;
		// 初始化内部状态
		this.soldOutState = new SoldOutState(this);
		this.noQuarterState = new NoQuarterState(this);
		this.hasQuarterState = new HasQuarterState(this);
		this.soldState = new SoldState(this);
		this.winnerSoldState = new WinnerSoldState(this);
		
		this.count = numberGumballs;
		if(numberGumballs > 0) {
			// 如果机器里有糖果，则设置初始状态为noQuarterState，在该state下，唯一允许执行的行为是“insertQuarter”
			this.state = noQuarterState; 
		}
	}
	
	/**
	 * 投入硬币
	 */
	public void insertQuarter() {
		state.insertQuarter();
	}
	
	/**
	 * 退回硬币
	 */
	public void ejectQuarter() {
		state.ejectQuarter();
	}
	
	/**
	 * 转动曲柄
	 */
	public void turnCrank() {
		try {
			state.turnCrank();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		// dispense()是一个内部动作，不需要开放给客户端:转动曲柄与释放糖果是一个连续的动作。
		state.dispense();
	}
	
	/**
	 * 释放出糖果
	 */
	public void releaseBall() {
		System.out.println("糖果已释放，请查收");
		if(count > 0) {
			count = count - 1;
		}
	}
	
	public void refill(int count) {
		if(count <= 0) {
			throw new IllegalArgumentException("糖果数量必须大于0");
		}
		int total = this.count + count;
		System.out.println("\n【refilling...】当前剩余"+this.count+"颗，装填"+count+"颗，总共剩余"+total+"颗");
		this.count = total;
		this.state = noQuarterState;
	}
	
	@Override
	public String toString() {
		String desc = "\nMighty Gumball, Inc.\n"
				+ "Java-enabled Standing Gumball Model #2016\n"
				+ "Inventory:" + this.getCount() + " gumballs\n"
				+ "Machine is waiting for quarter";
		return desc;
	}
	
	// getters & setters
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public State getSoldOutState() {
		return soldOutState;
	}

	public State getNoQuarterState() {
		return noQuarterState;
	}

	public State getHasQuarterState() {
		return hasQuarterState;
	}

	public State getSoldState() {
		return soldState;
	}

	public int getCount() {
		return count;
	}

	public State getWinnerSoldState() {
		return winnerSoldState;
	}

	public String getLocation() {
		return location;
	}

}
