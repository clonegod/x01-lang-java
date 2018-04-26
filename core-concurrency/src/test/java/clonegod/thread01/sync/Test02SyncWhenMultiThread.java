package clonegod.thread01.sync;
/**
 * 关键字synchronized取得的锁都是对象锁，而不是把一段代码（方法）当做锁，
 * 所以代码中哪个线程先执行synchronized关键字的方法，哪个线程就持有该方法所属对象的锁（Lock），
 * 
 * 在静态方法上加synchronized关键字，表示锁定.class类，类一级别的锁（独占.class类）。
 *
 */
public class Test02SyncWhenMultiThread {

	private int num = 0;
	
	public /** static */ synchronized void printNum(String tag){
		try {
			
			if(tag.equals("a")){
				num = 100;
				System.out.println("tag a, set num over!");
				Thread.sleep(1000);
			} else {
				num = 200;
				System.out.println("tag b, set num over!");
			}
			
			System.out.println("tag " + tag + ", num = " + num);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//注意观察run方法输出顺序
	public static void main(String[] args) {
		
		//不同的对象，关联的是不同的锁。每个线程分别获取锁之后，执行synchronized方法体的内容。
		final Test02SyncWhenMultiThread m1 = new Test02SyncWhenMultiThread();
		final Test02SyncWhenMultiThread m2 = new Test02SyncWhenMultiThread();
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				m1.printNum("a");
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			@Override 
			public void run() {
				m2.printNum("b");
			}
		});		
		
		t1.start();
		t2.start();
		
	}
	
	
	
	
}
