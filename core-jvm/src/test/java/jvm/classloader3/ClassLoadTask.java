package jvm.classloader3;

import java.lang.reflect.Method;

public class ClassLoadTask implements Runnable {
		String clsBaseDir;
		String[] watchedCls;
	
		public ClassLoadTask(String clsBaseDir, String[] watchedCls) {
			this.clsBaseDir = clsBaseDir;
			this.watchedCls = watchedCls;
		}

		public void run(){ 
			while(true) {
			    try { 
			        // 每次都创建出一个新的类加载器
			        CustomClsHotSwaper cl = new CustomClsHotSwaper(clsBaseDir, watchedCls); 
			        
			        // 加载字节码文件
			        Class<?> cls = cl.loadClass(watchedCls[0]); 
			        
			        // 实例化
			        Object newInstance = cls.newInstance();
			        
			        // 调用方法
			        Method m = newInstance.getClass().getMethod("doit", new Class[]{int.class}); 
			        m.invoke(newInstance, new Object[]{TestHotSwaper.COUNTER.incrementAndGet()}); 
			        
			        Thread.sleep(1000);
			    }  catch(Exception ex) { 
			        ex.printStackTrace(); 
			    } 
			}
		}
	}