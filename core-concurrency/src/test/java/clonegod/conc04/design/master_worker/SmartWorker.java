package clonegod.conc04.design.master_worker;

import clonegod.concurrent.Task;

/**
 * 提供任务执行的具体功能/算法
 *
 */
public class SmartWorker extends Worker {

	@Override
	protected Object doWork(Task task) {
		Object result = null;
		try {
			//处理任务的耗时。。 比如说进行操作数据库。。。
			Thread.sleep(500);
			// 模拟结果
			result = Integer.valueOf(task.getContent());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}

}
