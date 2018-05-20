package thread01.volatil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile关键字不具备synchronized关键字的原子性（同步）
 *
 */
public class TesstVolatileIsNotAtomic extends Thread{
	//private static volatile int count;
	private static AtomicInteger count = new AtomicInteger(0);
	private static void addCount(){
		for (int i = 0; i < 1000; i++) {
			//count++ ;
			count.incrementAndGet();
		}
		System.out.println(count);
	}
	
	public void run(){
		addCount();
	}
	
	public static void main(String[] args) {
		
		TesstVolatileIsNotAtomic[] arr = new TesstVolatileIsNotAtomic[100];
		for (int i = 0; i < 10; i++) {
			arr[i] = new TesstVolatileIsNotAtomic();
		}
		
		for (int i = 0; i < 10; i++) {
			arr[i].start();
		}
	}
	
}