package com.aysnclife.dataguru.jvm;

import java.lang.reflect.Method;

public class JVM06_HelloMain {
	
	public static void main(String[] args) throws Exception {
		new Thread(new Loader()).start();
		for(;;) {
			JVM06_Worker.doit();
			Thread.sleep(3000);
		}
	}
	
	
	static class Loader implements Runnable {
		public void run(){ 
			while(true) {
			    try { 
			        // 每次都创建出一个新的类加载器
			    	String clsBaseDir = "E:/practice/dataguru/target/classes";
			    	String watchedCls[] = {"com.aysnclife.dataguru.jvm.JVM06_Worker"};
			        CustomHotswapCL cl = new CustomHotswapCL(clsBaseDir, watchedCls); 
			        
			        // 加载字节码文件
			        Class<?> cls = cl.loadClass(watchedCls[0]); 
			        
			        // 实例化
			        Object foo = cls.newInstance();
			        
			        // 调用方法
			        Method m = foo.getClass().getMethod("doit", new Class[]{}); 
			        m.invoke(foo, new Object[]{}); 
			        
			        Thread.sleep(1000);
			    }  catch(Exception ex) { 
			        ex.printStackTrace(); 
			    } 
			}
		}
	}
}
