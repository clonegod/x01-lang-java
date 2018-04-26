package com.aysnclife.dataguru.jvm.classloader;

import java.util.HashSet;
import java.util.Set;

public class MyClassReloadingFactory {

	public static Object newInstance() {
		String baseDir = "E:/practice/dataguru/target/classes";
		String dynamicClass = "com.aysnclife.dataguru.jvm.classloader.Worker";
		
		Set<String> dynamicLoadClasses = new HashSet<>();
		dynamicLoadClasses.add(dynamicClass);
		
		// get parent classloader
		ClassLoader parentClassLoader = MyClassLoader.class.getClassLoader();
		
	    MyClassLoader classLoader = new MyClassLoader(parentClassLoader, baseDir, dynamicLoadClasses);
	    
	    try {
	    	
	    	Class<?> myObjectClass = classLoader.loadClass(dynamicClass);
			
	    	return myObjectClass.newInstance();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	    return null;
	}
}
