package design.future;

/**
 * FutureClient
 * 	对客户端隐藏了异步任务的细节
 *
 */
public class FutureClient {

	public Result request(final String queryStr){
		//1 构造一个Result接口的实现类，用来返回给发送请求的客户端
		// 客户端可以继续其它任务，等需要结果的时候调用getResult()获取结果。
		final FutureResult futureResult = new FutureResult();
		
		//2 启动一个新的线程，执行任务.任务执行完成后，将结果设置到futureResult中。
		AsyncTask asyncTask = new AsyncTask(queryStr, futureResult);
		new Thread(asyncTask).start();
		
		return futureResult;
	}
	
}
