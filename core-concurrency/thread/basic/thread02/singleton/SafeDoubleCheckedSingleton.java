package thread02.singleton;

/**
 * 单例-懒汉式
 * -基于volatile的延迟初始化的方案
 *  双重检查 + volatile 修饰变量
 *
 */
public class SafeDoubleCheckedSingleton {
	
	private SafeDoubleCheckedSingleton() {}

	private static volatile SafeDoubleCheckedSingleton ds;
	
	public static SafeDoubleCheckedSingleton getDs(){
		if(ds == null){
			init();
			synchronized (SafeDoubleCheckedSingleton.class) {
				if(ds == null){
					ds = new SafeDoubleCheckedSingleton();
				}
			}
		}
		return ds;
	}

	private static void init() {
		try {
			//模拟初始化对象的准备时间...
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(SafeDoubleCheckedSingleton.getDs().hashCode());
			}
		},"t1");
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(SafeDoubleCheckedSingleton.getDs().hashCode());
			}
		},"t2");
		
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(SafeDoubleCheckedSingleton.getDs().hashCode());
			}
		},"t3");
		
		t1.start();
		t2.start();
		t3.start();
	}
	
}
