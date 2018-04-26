package clonegod.conc04.design.master_worker;

import java.util.Random;

import clonegod.concurrency.util.CommonUtil;
import clonegod.concurrent.Task;

public class TestMasterWorker {

	public static void main(String[] args) {
		int nThreads = Runtime.getRuntime().availableProcessors();
		System.out.println("availableProcessors=" + nThreads);
		Worker worker = new SimpleWorker();
		Master master = new Master(worker, nThreads);
		
		// 提交100个任务
		Random r = new Random();
		for(int i = 1; i <= 100; i++){
			Task t = new Task(i, "我的任务", String.valueOf(r.nextInt(1000)));
			master.submitTask(t);
		}
		// 启动所有的工作线程执行任务
		master.startWorkers();
		long start = System.currentTimeMillis();
		
		// 获取并行计算结果
		while(true){
			if(master.isComplete()){
				long end = System.currentTimeMillis() - start;
				long finalResult = master.getResult();
				System.out.println("最终结果：" + finalResult + ", 执行时间：" + end);
				break;
			}
			CommonUtil.sleep(10);
		}

	}
	
}
