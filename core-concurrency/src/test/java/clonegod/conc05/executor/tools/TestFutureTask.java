package clonegod.conc05.executor.tools;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import clonegod.concurrency.util.CommonUtil;

/**
 * Future模式- 适合处理非常耗时的任务，以异步方式执行任务，调用者之后再异步获取结果。
 * 
 * 将异步任务提交给线程池并行执行----实现多个任务的并行执行，以提高系统吞吐量。
 * 	execute - Runnable 无返回结果
 *	submit  - Callable 有异步结果
 */
public class TestFutureTask {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		String queryStr = "query";
		
		//创建一个固定线程的线程池且线程数为1,
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		// 提交两个异步任务并行执行
		Future<String> f1 = executor.submit(new MyFutureTask(queryStr));
		Future<String> f2 = executor.submit(new MyFutureTask(queryStr));
		System.out.println("提交N个并行任务到线程池");
		
		//这里可以做额外的数据操作，也就是主程序执行其他业务逻辑
		System.out.println("主线程处理其它的业务逻辑...");
		CommonUtil.sleep(1000);
		
		//调用获取数据方法,如果call()方法没有执行完成,则阻塞等待
		System.out.println("数据：" + f1.get());
		System.out.println("数据：" + f2.get());
		
		System.out.println("并行执行异步任务完成");
		
		// 关闭线程池
		executor.shutdown();
	}
	
	
	static class MyFutureTask implements Callable<String> {
		String request;
		public MyFutureTask(String request) {
			this.request = request;
		}
		/**
		 * 执行真实的业务逻辑，其执行可能很慢
		 */
		@Override
		public String call() throws Exception {
			//模拟执行耗时
			CommonUtil.sleep(5000);
			String result = this.request + "处理完成";
			return result;
		}
	}
}
