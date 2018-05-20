package design.master_worker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import queue.blocking.priority.Task;

/**
 *	Master-Worker模式
 *		常用来做并行任务计算。
 *	核心思想：
 *		系统由两类线程协作完成任务。
 *		Master线程负责接收和分派任务，
 *		Worker负责处理Master分配的任务。
 *		当各个Worker处理完成后，将结果返回给Master，由Master进行结果的汇总。
 *
 * 	场景：
 * 		将一个大任务分解成若干小任务，并行执行，从而提高系统的吞吐量。
 * 	
 * 	问：Hadoop 的 Map-Reduce 并行计算 属于Master-Worker模型吗？
 * 		 map阶段由不同节点完成子任务计算，reduce阶段由Master完成结果汇总！
 *
 */
public class Master {
	
	// 接收客户端提交任务的容器---任务是一个一个提交的，因此使用队列来存储任务，这里选择使用高并发队列。
	private ConcurrentLinkedQueue<Task> workQueue = new ConcurrentLinkedQueue<>();
	
	// 存放执行具体任务的worker线程的容器
	private HashMap<String, Thread> workers = new HashMap<>();
	
	// 存放工作线程处理完任务返回的结果--多线程并发往该集合写入数据，所以需要使用高并发集合
	private ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<>();
	
	/**
	 * 
	 * @param worker	工作者线程任务对象
	 * @param workerCount	线程数量
	 */
	public Master(Worker worker, int workerCount) {
		worker.setWorkQueue(this.workQueue);
		worker.setResultMap(this.resultMap);
		// 创建一组工作线程
		for( int i = 0; i < workerCount; i++ ) {
			workers.put(Integer.toString(i), new Thread(worker));
		}
	}
	
	
	public Master submitTask(Task task) {
		workQueue.add(task);
		return this;
	}
	
	/**
	 * 启动工作者线程执行任务
	 */
	public void startWorkers() {
		for(Map.Entry<String, Thread> me : this.workers.entrySet()) {
			me.getValue().start();
		}
	}
	
	/**
	 * 判断是否所有工作线程都已经全部完成任务
	 * 
	 * @return
	 */
	public boolean isComplete() {
		for(Map.Entry<String, Thread> me : this.workers.entrySet()) {
			if(me.getValue().getState() != Thread.State.TERMINATED) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 汇集工作线程返回的处理结果，返回给客户端
	 * @return
	 */
	public long getResult() {
		long result = 0L;
		for(Map.Entry<String, Object> me : this.resultMap.entrySet()) {
			result += (Integer)me.getValue();
		}
		return result;
	}
	
}
