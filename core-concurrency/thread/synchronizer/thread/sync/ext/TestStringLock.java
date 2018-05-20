package thread.sync.ext;
/**
 * synchronized代码块对字符串的锁，注意String常量池的缓存功能
 *
 */
public class TestStringLock {

	public void method() {
		//new String("字符串常量")
		synchronized ("字符串常量") {
			try {
				while(true){
					System.out.println("当前线程 : "  + Thread.currentThread().getName() + "开始");
					Thread.sleep(1000);		
					System.out.println("当前线程 : "  + Thread.currentThread().getName() + "结束");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		final TestStringLock stringLock = new TestStringLock();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				stringLock.method();
			}
		},"t1");
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				stringLock.method();
			}
		},"t2");
		
		t1.start();
		t2.start();
	}
}
