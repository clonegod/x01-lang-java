package clonegod.conc04.design.master_worker;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import clonegod.concurrent.Task;

public class Worker implements Runnable {
	
	// Master维护的任务队列，每个工作线程从该并发队列获取任务
	private ConcurrentLinkedQueue<Task> workQueue;
	
	// 每个工作线程处理完的结果存入该并发集合
	private ConcurrentHashMap<String, Object> resultMap;
	
	public void setWorkQueue(ConcurrentLinkedQueue<Task> workQueue) {
		this.workQueue = workQueue;
	}


	public void setResultMap(ConcurrentHashMap<String, Object> resultMap) {
		this.resultMap = resultMap;
	}
	
	/**
	 * 执行任务
	 */
	@Override
	public void run() {
		while(true) {
			Task task = workQueue.poll();
			if(task == null) {
				break; // 任务队列没有更多任务，在当前线程执行结束
			}
			Object result = doWork(task);
			this.resultMap.put(Integer.toString(task.getId()), result);
		}
	}

	// 任务如何完成，可实现不同的子类来提供具体实现方案
	protected Object doWork(Task task) {
		throw new UnsupportedOperationException("请实现具体子类来完成任务的执行逻辑");
	}

	
}
