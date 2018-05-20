package thread.sync.ext;
/**
 * 锁对象的改变问题
 *
 */
public class ChangeLock {

	private String lock = "lock";
	
	private void method(){
		synchronized (lock) {
			try {
				System.out.println("当前线程 : "  + Thread.currentThread().getName() + "开始");
				lock = "change lock"; // 更新锁导致锁失效，多线程竞争的不是同一个锁
				Thread.sleep(2000);
				System.out.println("当前线程 : "  + Thread.currentThread().getName() + "结束");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
	
		final ChangeLock changeLock = new ChangeLock();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				changeLock.method();
			}
		},"t1");
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				changeLock.method();
			}
		},"t2");
		
		t1.start();
		
		Thread.sleep(100);
		
		t2.start();
	}
	
}
