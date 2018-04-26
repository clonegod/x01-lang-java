package clonegod.thread01.sync.ext;

/**
 * 死锁问题，在设计程序时就应该避免双方相互持有对方的锁的情况
 *
 */
public class TestDeadLock implements Runnable{

	private String tag;
	private static Object lock1 = new Object();
	private static Object lock2 = new Object();
	
	public TestDeadLock(String tag) {
		this.tag = tag;
	}

	@Override
	public void run() {
		if(tag.equals("a")){
			synchronized (lock1) {
				try {
					System.out.println("当前线程 : "  + Thread.currentThread().getName() + " 进入lock1执行");
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("当前线程 : "  + Thread.currentThread().getName() + " 尝试获取lock2");
				synchronized (lock2) {
					System.out.println("当前线程 : "  + Thread.currentThread().getName() + " 进入lock2执行");
				}
			}
		}
		if(tag.equals("b")){
			synchronized (lock2) {
				try {
					System.out.println("当前线程 : "  + Thread.currentThread().getName() + " 进入lock2执行");
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("当前线程 : "  + Thread.currentThread().getName() + " 尝试获取lock1");
				synchronized (lock1) {
					System.out.println("当前线程 : "  + Thread.currentThread().getName() + " 进入lock1执行");
				}
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		Thread t1 = new Thread(new TestDeadLock("a"), "t1");
		Thread t2 = new Thread(new TestDeadLock("b"), "t2");
		 
		t1.start();
		
		Thread.sleep(500);
		
		t2.start();
	}
	

	
}
