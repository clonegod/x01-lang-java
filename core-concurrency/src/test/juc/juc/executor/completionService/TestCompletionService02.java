package juc.executor.completionService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Suppose instead that you would like to use the first non-null result of the
 * set of tasks, ignoring any that encounter exceptions, and cancelling all
 * other tasks when the first one is ready:
 */
public class TestCompletionService02 {

	/**
	 * 批量提交任务，每当有任务完成，便可对任务进行处理。
	 * 只要返回了有效结果，就取该结果，并结束其它线程的任务。
	 * 
	 * @param executor
	 *            线程执行器
	 * @param solvers
	 *            需要被执行的Task集合
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	void solve(Executor e, Collection<Callable<Result>> solvers) throws InterruptedException {
		
		CompletionService<Result> ecs = new ExecutorCompletionService<Result>(e);
		
		int n = solvers.size();
		List<Future<Result>> futures = new ArrayList<Future<Result>>(n);
		
		Result result = null;
		try {
			for (Callable<Result> s : solvers)
				futures.add(ecs.submit(s));
			
			/** 只需要某个线程返回有效结果即可 */
			for (int i = 0; i < n; ++i) {
				try { 
					Result r = ecs.take().get();
					if (r != null) {
						result = r;
						break;
					}
				} catch (ExecutionException ignore) {
				}
			}
		} finally {
			for (Future<Result> f : futures)
				f.cancel(true); // Attempts to cancel execution of this task.
		}

		if (result != null)
			use(result);
	}

	private void use(Result r) {
		System.out.println(r.toString());
	}

	public static void main(String[] args) throws Exception {

		TestCompletionService02 instance = new TestCompletionService02();

		ExecutorService pool = Executors.newCachedThreadPool();
		Collection<Callable<Result>> solvers = new LinkedList<Callable<Result>>();

		solvers.add(new Task());
		solvers.add(new Task());
		solvers.add(new Task());

		instance.solve(pool, solvers);

		pool.shutdown();
	}
}
