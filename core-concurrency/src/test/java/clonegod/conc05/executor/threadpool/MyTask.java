package clonegod.conc05.executor.threadpool;

import clonegod.concurrency.util.CommonUtil;

public class MyTask implements Runnable {
	int id;
	String name;
	
	public MyTask(int id) {
		super();
		this.id = id;
	}
	
	public MyTask(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public void run() {
		CommonUtil.sleep(5000);
		System.out.println(Thread.currentThread().getName() + " mytask done..., task id=" + id);
	}
	
	@Override
	public String toString() {
		return "MyTask [id=" + id + "]";
	}

	public int getId() {
		return id;
	}
	
}