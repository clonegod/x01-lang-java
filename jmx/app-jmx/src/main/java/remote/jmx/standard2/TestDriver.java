package remote.jmx.standard2;

import java.lang.management.ManagementFactory;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.management.MBeanServer;
import javax.management.ObjectName;


public class TestDriver {
	public static void main(String[] args) throws Exception {
		ObjectName name = ObjectName.getInstance("Test:name=hello");
		HelloMBean mBean = new Hello();

		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		mBeanServer.registerMBean(mBean, name);
		
		new Runner((Hello)mBean).start();
		
		Thread.sleep(5000000);
	}
	
	/**
	 * 此线程类读取Hello中的属性，只要客户端设置了新的属性值，本线程就能获取到 
	 */
	private static class Runner extends Thread {
		Hello hello;
		boolean flag = true;
		
		public Runner(Hello hello) {
			this.hello = hello;
		}
		
		public void run() {
			while(flag) {
				try {
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(hello.isStopNow()) {
					flag = false;
				} else {
					String name = hello.getName();
					if(Objects.equals(name, null) || "".equals(name.trim())) {
						System.out.println("Name is not set yet!");
					} else {
						hello.printHelloWorld();
					}
				}
			}
		}
		
	}
	
	
}