package state2.state;

import java.io.Serializable;

public abstract class State implements Serializable {
	private static final long serialVersionUID = -9221712825687114015L;

	/**
	 * 投入硬币
	 */
	public void insertQuarter() {
		throw new UnsupportedOperationException(
				this.getClass().getSimpleName()
				+ "===>"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
	}
	
	/**
	 * 退回硬币
	 */
	public void ejectQuarter() {
		throw new UnsupportedOperationException(
				this.getClass().getSimpleName()
				+ "===>"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
	}
	
	/**
	 *  转动曲柄
	 *  @retrun 转动是否有效：投入硬币转动曲柄为有效操作，否则无效
	 */
	public void turnCrank() {
		throw new UnsupportedOperationException(
				this.getClass().getSimpleName()
				+ "===>"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
	}
	
	/**
	 * 释放糖果
	 */
	public void dispense() {
		throw new UnsupportedOperationException(
				this.getClass().getSimpleName()
				+ "===>"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
	}
}
