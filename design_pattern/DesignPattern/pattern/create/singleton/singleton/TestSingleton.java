package singleton;

import java.io.File;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class TestSingleton {
	
	static int nThreads = 8000;
	static CountDownLatch cdl;
	static Set<Integer> objSet;
	
	/**
	 * 10000个线程同时调用单例类获取对象，如果返回的全部是同一个对象，则说明单例类是安全的。
	 */
	public static void main(String[] args) throws Exception {
		ThreadPoolExecutor tpe = (ThreadPoolExecutor) Executors.newFixedThreadPool(nThreads);
		tpe.prestartAllCoreThreads();
		
		// 1. 静态内部类实现单例
		doTest(tpe, () -> {
			objSet.add(InnerClassHolderMan.getInstance().hashCode());
			cdl.countDown();
		});
		
		// 2. 饿汉式单例
		doTest(tpe, () -> {
			objSet.add(EagerMan.getInstance().hashCode());
			cdl.countDown();
		});
		
		
		// 3. 懒汉式单例
		doTest(tpe, () -> {
			objSet.add(LazyMan.getInstance().hashCode());
			cdl.countDown();
		});
		
		// 4. 枚举实现单例对象---注册式单例的一种实现形式
		doTest(tpe, () -> {
			objSet.add(RegisterManEnum.BLACK.hashCode());
			cdl.countDown();
		});
		
		// 5. 单例对象的注册容器---注册式单例
		doTest(tpe, () -> {
			objSet.add(RegisterMan.getInstance(Object.class.getName()).hashCode());
			cdl.countDown();
		});
		
		// 6. 保证单例对象在序列化之后再进行反序列化，得到的仍然是唯一的那个单例对象
		File file = new File("single.obj");
		DeepCloneHelper.ser(file);
		Object obj = DeepCloneHelper.deser(file);
		System.out.println(obj == SerializeMan.getInstance());
		
		tpe.shutdownNow();
	}
	
	
	public static void doTest(ThreadPoolExecutor tpe, Runnable r) throws Exception {
		cdl = new CountDownLatch(nThreads);
		objSet = new CopyOnWriteArraySet<>();
		
		for(int i = 0; i < nThreads; i++) {
			tpe.execute(r);
		}
		
		cdl.await();
		
		if(objSet.size() != 1) {
			System.err.println("Singleton Impl is not Thread Not Safe");
		} else {
			System.out.println("ok");		
		}
		
	}
	
}
