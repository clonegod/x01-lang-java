package com.aysnclife.dataguru.jvm;

import javassist.ClassPool;

/**
 * 演示运行时动态加载类造成java.lang.OutOfMemoryError: PermGen space
 */
public class JVM02_01 {
	static {
		//new OutOfMemoryError().printStackTrace();
	}
	
	public static void main(String[] args) throws Exception {
		try {
			for (int i = 0; i < 20000; i++) {
				generate("com.dataguru.jvm.demo.Generated" + i);
			}
		} catch (Error e) {
			e.printStackTrace();
		}
	}

	public static Class<?> generate(String name) throws Exception {
		ClassPool pool = ClassPool.getDefault();
		return pool.makeClass(name).toClass();
	}
	
}

