package jvm.classloader3;

import java.util.concurrent.atomic.AtomicInteger;

public class TestHotSwaper {
	
	public static AtomicInteger COUNTER = new AtomicInteger(0);
	
	public static void main(String[] args) throws Exception {
		String classDir = System.getProperty("user.dir") + "/target/test-classes";
		String[] classNames = new String[] {"jvm.classloader3.ChangeableClass"};
		
		Runnable r = new ClassLoadTask(classDir, classNames);
		
		// 启动一个线程实时加载类，类修改后加载得到的是最新的类，因此输出的结果是动态变化的。
		new Thread(r).start();
		
		// 主线程使用最早加载的类，执行doit方法，输出结果是不变的。
		for(;;) {
			ChangeableClass.doit(0);
			Thread.sleep(3000);
		}
	}
	
}
