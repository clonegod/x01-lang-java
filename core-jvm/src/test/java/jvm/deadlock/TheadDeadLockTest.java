package jvm.deadlock;

import java.util.Random;

/**
 * 演示:死锁
 */
public class TheadDeadLockTest {
	
	static final Object LOCK_NORTH = new Object();
	static final Object LOCK_WEST = new Object();
	static final Object LOCK_SOUTH = new Object();
	static final Object LOCK_EAST = new Object();
	
	public static void main(String[] args) throws Exception {
		Thread northCar = new Thread(new Car(LOCK_NORTH, LOCK_WEST), "NorthCar");
		Thread westCar = new Thread(new Car(LOCK_WEST, LOCK_SOUTH),  "WestCar");
		Thread southCar = new Thread(new Car(LOCK_SOUTH, LOCK_EAST), "SouthCar");
		Thread eastCar = new Thread(new Car(LOCK_EAST, LOCK_NORTH),  "EastCar");
		
		northCar.start();
		westCar.start();
		southCar.start();
		eastCar.start();
		
		Thread.sleep(3000);
		System.out.println("Thread-Main Over");
	}
}

class Car implements Runnable {
	private Object current;
	private Object next;
	
	public Car(Object current, Object next) {
		this.current = current;
		this.next = next;
	}

	@Override
	public void run() {
		while(true) {
			synchronized (current) {
				System.out.println(Thread.currentThread().getName() + " running");
				synchronized (next) {
					System.out.println(Thread.currentThread().getName() + " running");
				}
			}
			try {
				Thread.sleep(new Random().nextInt(10));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}