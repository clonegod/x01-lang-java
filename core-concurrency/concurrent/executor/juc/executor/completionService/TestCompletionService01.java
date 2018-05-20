package juc.executor.completionService;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Suppose you have a set of solvers for a certain problem, 
 * each returning a value of some type Result, 
 * and would like to run them concurrently, 
 * processing the results of each of them that return a non-null value, 
 * in some method use(Result r). 
 */
public class TestCompletionService01 {
	
	/**
	 * 批量提交任务，每当有任务完成，便可对任务进行处理。当所有任务都返回成功后，才接着往下执行。
	 * 
	 * @param executor	线程执行器
	 * @param solvers 需要被执行的Task集合
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	 void solve(Executor executor, Collection<Callable<Result>> solvers) 
			 throws InterruptedException, ExecutionException {
		 
		 //BlockingQueue<Future<Result>> completionQueue = new LinkedBlockingQueue<Future<Result>>();
		 //CompletionService<Result> ecs = new ExecutorCompletionService<Result>(executor, completionQueue);
		 
	     CompletionService<Result> ecs = new ExecutorCompletionService<Result>(executor);
	     
	     for (Callable<Result> s : solvers) {
	    	 ecs.submit(s);
	     }
	     
	     int n = solvers.size();
	     
	     for (int i = 0; i < n; ++i) {
	         Result r = ecs.take().get();
	         if (r != null)
	             use(r);
	     }
	     
	     System.out.println("任务队列中的所有任务都执行结束");
	 }

	
	private void use(Result r) {
		System.out.println(r.toString());
	}



	public static void main(String[] args) throws Exception {
		
		TestCompletionService01 instance = new TestCompletionService01();
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		Collection<Callable<Result>> solvers = new LinkedList<Callable<Result>>();
		
		solvers.add(new Task());
		solvers.add(new Task());
		solvers.add(new Task());
		
		instance.solve(executor, solvers);
		
		executor.shutdown();
	}
}
