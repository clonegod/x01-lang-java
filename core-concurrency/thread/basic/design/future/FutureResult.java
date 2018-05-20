package design.future;

public class FutureResult implements Result {
	
	private boolean isReady = false;

	private AsyncTask asyncTask ;
	
	/**
	 * 异步任务执行完成后，会调用此方法设置最终的结果
	 * 
	 */
	public synchronized void setAsyncResult(AsyncTask asyncTask) {
		//如果已经装载完毕了，就直接返回
		if(isReady){
			return;
		}
		// 设置异步任务的结果
		this.asyncTask = asyncTask;
		isReady = true;
		// 通知异步任务执行完毕
		notify();
	}
	
	/**
	 * 客户端获取异步任务结果。
	 * 	如果异步任务尚未执行难完成，则阻塞当前获取结果的线程
	 */
	@Override
	public synchronized String getResult() {
		// 异步任务未执行完成， 程序就一直处于阻塞状态
		while(!isReady){
			try {
				System.out.println(Thread.currentThread().getName() + " 异步任务尚未执行完毕，获取结果的线程进入阻塞状态");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//装载好直接获取数据即可
		return this.asyncTask.getResult();
	}

}
