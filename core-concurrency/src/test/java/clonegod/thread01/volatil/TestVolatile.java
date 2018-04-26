package clonegod.thread01.volatil;

/**
 * 	volatile 
 * 底层实现：强制线程从主内存读取变量的值，而不是从线程本地工作内存取值。
 * 用途：同一个变量在多个线程之间的可见性（更新后的值在不同线程中都立即可见）。
 * 特点：仅具备变量在多线程间的可见性，不具有变量操作的原子性。
 * 
 */
public class TestVolatile extends Thread {
	
	// isRunning 变量的值被多个线程操作
	private volatile boolean isRunning = true;
	
	private void setRunning(boolean isRunning){
		this.isRunning = isRunning;
	}
	
	@Override
	public void run(){
		System.out.println("进入run方法..");
		while(isRunning == true){
			//..
		}
		System.out.println("线程停止");
	}
	
	public static void main(String[] args) throws InterruptedException {
		TestVolatile rt = new TestVolatile();
		rt.start();
		
		Thread.sleep(1000);
		rt.setRunning(false);
		
		System.out.println("isRunning的值已经被设置了false");
	}
	
	
}
