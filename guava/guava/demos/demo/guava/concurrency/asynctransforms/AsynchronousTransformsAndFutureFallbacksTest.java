package demo.guava.concurrency.asynctransforms;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;

import demo.guava.basic.object.Person;

public class AsynchronousTransformsAndFutureFallbacksTest {

	static ListeningExecutorService listeningExecutorService;
	
	static int NUM_THREADS = 3;
	
	public static void main(String[] args) throws Exception {
		
		listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(NUM_THREADS));
		
		ListenableFuture<String> listenableFutureOfNames =
				listeningExecutorService.submit(new Callable<String>() {
				@Override
				public String call() throws Exception {
					// 随机返回一个Name，如果返回null,在PersonAsyncFunction中将抛出异常
					// Futures的Fallback配置为在遇到NullPointerException时，默认返回bob
					List<String> names = Lists.newArrayList("Alice", null, "Cindy");
					Collections.shuffle(names);
					return names.get(0);
				}
			});
		
		final AsyncFunction<String, Person> asyncFunctionForPerson = new PersonAsyncFuntion();
		
		// 通过字符串，异步函数处理后得到Person
		// Futures.transform(ListenableFuture<String> f, AsyncFunction<String,Person> af);
		ListenableFuture<Person> listenableFutureOfTransform =
				Futures.transformAsync(
						listenableFutureOfNames, 
						asyncFunctionForPerson, 
						listeningExecutorService);
		
		// Applying FutureFallbacks 
		// 处理异步任务时发生的异常：返回默认值， 或者抛出异常
		ListenableFuture<Person> listenableFutureOfTransformWithFallback = 
				Futures.catchingAsync(
						listenableFutureOfTransform, 
						NullPointerException.class,
					    e -> {
					       if (e instanceof NullPointerException) {
					         return getDefaultPersonFuture();
					       }
					       throw e;
					    },
					    listeningExecutorService);
		
		// 声明一个回调函数，用来处理异步任务最终返回的结果
		FutureCallback<Person> futureCallback = new FutureCallback<Person>() {
			@Override
			public void onSuccess(Person person) {
				System.out.println(person.getFirstName() + " successfully");
			}
			@Override
			public void onFailure(Throwable t) {
				System.err.println("Something Bad Happen:\n"+Throwables.getStackTraceAsString(t));
			}
		};
		
		// 执行异步任务
		Futures.addCallback(
				listenableFutureOfTransformWithFallback, 
				futureCallback, 
				listeningExecutorService);
		
		for(int i=0; i<3; i++) {
			System.out.println(Thread.currentThread().getName() + " 继续执行其它任务：" + i);
		}
		
		listeningExecutorService.awaitTermination(2, TimeUnit.SECONDS);
		listeningExecutorService.shutdown();
	}

	private static ListenableFuture<Person> getDefaultPersonFuture() {
		SettableFuture<Person> sf = SettableFuture.create();
		sf.set(new Person("Bob", 20));
		return sf;
	}
	

}
