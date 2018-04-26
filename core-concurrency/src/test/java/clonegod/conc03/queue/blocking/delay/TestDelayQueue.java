package clonegod.conc03.queue.blocking.delay;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

import clonegod.concurrency.util.CommonUtil;
import clonegod.concurrent.DelayTask;

/**
 * DelayQueue
 * 	延时的队列，该队列中的元素只有当其指定的延迟时间到了，消费者线程才能从队列中获取到元素。
 * 	往该队列添加的元素，必须实现Delayed接口。
 * 
 * 特点：
 * 1、无界队列
 * 2、具有延迟特性
 * 
 * 适用场景：
 * 	对缓存超时数据的清理
 * 	任务超时处理
 * 	空闲连接的关闭
 * 	...
 *
 */
public class TestDelayQueue {
	
	public static void main(String[] args) throws InterruptedException {
		
		/**
		 * 精确控制延迟发送短信的功能
		 */
		
		final DelayQueue<DelayTask> q = new DelayQueue<>();
		
		DelayTask task1 = new DelayTask("sms-01", "延迟1s发生短信给客户",  TimeUnit.SECONDS.toMillis(3));
		DelayTask task2 = new DelayTask("sms-02", "延迟5s发生短信给客户",  TimeUnit.SECONDS.toMillis(5));
		DelayTask task3 = new DelayTask("sms-03", "延迟10s发生短信给客户", TimeUnit.SECONDS.toMillis(10));
		
		q.put(task1);
		q.put(task2);
		q.put(task3);
		
		// 任务处理线程
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						DelayTask task = q.take();
						System.out.println(CommonUtil.currentTime() + ", 任务延迟时间到: " + task);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		Thread.sleep(TimeUnit.SECONDS.toMillis(11));
		System.exit(0);
	}
}
