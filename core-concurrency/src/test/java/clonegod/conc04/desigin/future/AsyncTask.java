package clonegod.conc04.desigin.future;

import java.util.Random;

/**
 * 异步任务
 *
 */
public class AsyncTask implements Runnable {

	private String input;
	
	private String result;
	
	/** 1、持有返回给客户端的结果对象，当任务执行完成后，将结果赋值给该对象 */
	private FutureResult futureResult;
	
	public AsyncTask (String queryStr, FutureResult futureResult){
		this.input = queryStr;
		this.futureResult = futureResult;
	}
	
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " 根据" + this.input + "进行查询，这是一个很耗时的操作..");
		try {
			Thread.sleep(5000);
			this.result = "Hello Future-" + new Random().nextInt(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			this.result = e.getMessage();
		} finally {
			System.out.println(Thread.currentThread().getName() + " 执行异步任务完毕");
			/** 2、异步任务执行完毕，赋值给futureResult。*/
			futureResult.setAsyncResult(this);
		}
	}
	
	public String getResult() {
		return this.result;
	}
}
