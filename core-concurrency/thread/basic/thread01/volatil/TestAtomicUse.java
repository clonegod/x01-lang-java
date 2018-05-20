package thread01.volatil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicInteger的单个方法可以保证原子性，但“多次调用”是没有原子性保障的。
 *
 */
public class TestAtomicUse {

	private static AtomicInteger count = new AtomicInteger(0);
	
	//多个addAndGet在一个方法内是非原子性的，需要加synchronized进行修饰，保证4个addAndGet整体原子性。
	//如果具有原子性，则每次返回结果是10的整数倍
	public /*synchronized*/ int multiAdd(){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count.addAndGet(1);
			count.addAndGet(2);
			count.addAndGet(3);
			count.addAndGet(4); //+10
			return count.get();
	}
	
	
	public static void main(String[] args) {
		
		final TestAtomicUse au = new TestAtomicUse();

		List<Thread> ts = new ArrayList<Thread>();
		for (int i = 0; i < 100; i++) {
			ts.add(new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println(au.multiAdd());
				}
			}));
		}

		for(Thread t : ts){
			t.start();
		}

	}
}
