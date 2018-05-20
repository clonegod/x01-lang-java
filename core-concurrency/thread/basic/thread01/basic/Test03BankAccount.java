package thread01.basic;

import java.util.Random;

public class Test03BankAccount {
	public static void main(String[] args) {
		final Bank bank = new Bank();
		
		SaveMoney save = new SaveMoney(bank);
		
		// 线程1执行存钱操作
		new Thread(save).start();
		
		// 线程2执行存钱操作
		new Thread(save).start();
	}
}

class Bank {
	private double total;
	
	/**
	 * total是共享资源，对total的读写操作需要加锁---方法上加锁
	 * 同步函数的锁是this
	 */
	public synchronized void add(double amount) {
		total = total + amount;
		try {
			Thread.sleep(new Random().nextInt(500));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+": total="+total);
	}
}

/**
 * 存钱操作
 */
class SaveMoney implements Runnable {
	
	// 共享资源
	private Bank bank;

	public SaveMoney(Bank bank) {
		this.bank = bank;
	}

	public void run() {
		for(int i=0; i<3; i++) {
			bank.add(100.00);
		}
	}
}