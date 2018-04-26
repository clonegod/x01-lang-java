package com.aysnclife.dataguru.jvm.classloader;

public class HelloMain {
	
	public static void main(String[] args) throws Exception {

		for(;;) {
			//create new class loader so classes can be reloaded.
			IWorker worker = (IWorker) MyClassReloadingFactory.newInstance();
			worker.doit();
			sleep(3000);
		}
	    
	}

	private static void sleep(long mills) {
		try {
			Thread.sleep(mills);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
