package clonegod.conc05.executor.threadpool;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 新提交任务被线程池拒绝执行时的处理方式
 * 
 * 如果任务通过submit提交，则参考下面的链接进行处理，否则无法获取到任务的相关信息(Runnable为FutureTask类型)。
 * 	https://stackoverflow.com/questions/47325987/how-to-get-the-tasks-field-in-the-rejectedexecutionhandler
 */
public class MyRejectExecutionHandler implements RejectedExecutionHandler {
	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
		handleRejectTask(r, e);
	}
	
	/**
	 * 关键点：被拒绝的任务不能被忽略，而应该采取有效的办法来查询处理。
	 * 
	 * 处理方式1：
	 * 	记录任务相关信息到日志文件中保存，在系统空闲时通过定时任务重新执行。（推荐）
	 * 
	 * 处理方式2：
	 * 	调用提交任务的客户端API，通知方任务处理失败，稍后重试。
	 * 
	 * @param r
	 * @param e
	 */
	private void handleRejectTask(Runnable r, ThreadPoolExecutor e) {
		StringBuilder buf = new StringBuilder();
		buf.append("----------------记录失败任务到文件------------------").append("\n");
		buf.append("线程池内部有界缓冲队列已满，任务被拒绝, task=" + r.toString()).append("\n");
		buf.append("Task " + r.toString() +
                " rejected from " +
                e.toString()).append("\n");
		
		System.out.println(buf.toString());
	}
}