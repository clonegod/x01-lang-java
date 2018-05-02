package java8.core.concurrency;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiFunction;
import java.util.function.LongBinaryOperator;
import java.util.stream.IntStream;

import org.junit.Test;

/**
 * 多线程并发安全工具类
 * 
 * Atomic Variables and ConcurrentMap
 * 
 * 	 AtomicInteger, AtomicBoolean, AtomicLong and AtomicReference.
 * 	 	对单个数值的更新，使用Atomic比使用锁（synchronized或lock）更高效
 *   
 *   LongAdder, LongAccumulator
 *   
 *	 ConcurrentMap, ConcurrentHashMap 	
 *		
 *		
 */
public class TestJava8ConcurrentPart3 {
	
	/**
	 * Internally, the atomic classes make heavy use of compare-and-swap (CAS)
	 */
	@Test
	public void testAtomicInteger_incrementAndGet() {
		
		AtomicInteger atomicInt = new AtomicInteger(0);

		ExecutorService executor = Executors.newFixedThreadPool(2);

		IntStream.range(0, 1000)
		    .forEach(i -> executor.submit(atomicInt::incrementAndGet));

		ConcurrentUtils.stop(executor);

		System.out.println(atomicInt.get());    // => 1000
		
	}
	
	@Test
	public void testAtomicInteger_updateAndGet() {
		AtomicInteger atomicInt = new AtomicInteger(0);

		ExecutorService executor = Executors.newFixedThreadPool(2);

		IntStream.range(0, 1000)
		    .forEach(i -> {
		        Runnable task = () ->
		            atomicInt.updateAndGet(n -> n + 2);
		        executor.submit(task);
		    });

		ConcurrentUtils.stop(executor);

		System.out.println(atomicInt.get());    // => 2000
		
	}
	
	@Test
	public void testAtomicInteger_accumulateAndGet() {
		AtomicInteger atomicInt = new AtomicInteger(0);

		ExecutorService executor = Executors.newFixedThreadPool(2);

		IntStream.rangeClosed(0, 100)
		    .forEach(i -> {
		        Runnable task = () ->
		            atomicInt.accumulateAndGet(i, (n, m) -> n + m);
		        executor.submit(task);
		    });

		ConcurrentUtils.stop(executor);

		System.out.println(atomicInt.get());    // => 5050
				
	}
		
	
	//=================================================
	
	/**
	 * LongAdder - 连续值的累加
	 * 	相比AtomicLong的改进：内部维护了一组变量，以减少线程间的竞争。
	 * 	适用于多线程并发环境下“写多读少”的情况。
	 * 	
	 * 	缺点：暂用更多的内存。	
	 * 
	 * 
	 * The class LongAdder as an alternative to AtomicLong can be used to consecutively add values to a number.
	 * This is often the case when capturing statistical data, 
	 * 	e.g. you want to count the number of requests served on a web server. 
	 */
	@Test
	public void testLongAdder() {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		LongAdder adder = new LongAdder();
		
		// 累加1000次
		IntStream.range(0, 1000)
		    .forEach(i -> executor.submit(adder::increment)); 

		ConcurrentUtils.stop(executor);

		System.out.println(adder.sum());   // => 1000
	}
	
	/**
	 * LongAccumulator - 更通用的LongAdder
	 * 	LongAccumulator is a more generalized version of LongAdder.
	 * 
	 * 
	 */
	@Test
	public void testLongAccumulator() {
		LongBinaryOperator op = (x, y) -> 10 * x + y;
		
		long initial = 0L;
		LongAccumulator accumulator = new LongAccumulator(op, initial);

		ExecutorService executor = Executors.newFixedThreadPool(2);

		IntStream.range(0, 10)
		    .forEach(i -> executor.submit(() -> accumulator.accumulate(i)));

		ConcurrentUtils.stop(executor);

		System.out.println(accumulator.getThenReset());     // => 123456789
	}
	
	
	//=================================================
	/**
	 * java8 对ConcurrentMap新增了API - 公共API
	 * 
	 * 	BiConsumer	
	 * 		Represents an operation that accepts two input arguments and returns no result. 
	 * 	BiFunctions 
	 * 		take two parameters and return a single value
	 */
	
	final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

	{
		map.put("foo", "bar");
		map.put("han", "solo");
		map.put("r2", "d2");
		map.put("c3", "p0");
	}
	
	@Test
	public void testCouncurrentMap() {
		// It can be used as a replacement to for-each loops to iterate over the entries of the concurrent map. 
		map.forEach((key, value) -> System.out.printf("%s = %s\n", key, value));
		
		// puts a new value into the map only if no value exists for the given key
		String value = map.putIfAbsent("c3", "p1");
		System.out.println(value);    // p0
		
		//  In case no entry exists for this key the passed default value is returned
		value = map.getOrDefault("hi", "there");
		System.out.println(value);    // there
		
		// Replaces each entry's value with the result of invoking the given function on that entry until all entries have been processed or the function throws an exception
		map.replaceAll((key, val) -> "r2".equals(key) ? "d3" : val);
		System.out.println(map.get("r2"));    // d3
		
		// compute() only transform a single entry 
		// two variants exist: computeIfAbsent() and computeIfPresent()
		map.compute("foo", (key, val) -> val + val);
		System.out.println(map.get("foo"));   // barbar
		
		// Merge accepts a key, and the new value to be merged into the existing entry 
		// and a bi-function to specify the merging behavior of both values
		// 两种情况：
		// 	1、key不存在，那么设置new value为key的value
		// 	2、key存在，那么调用bi-funciton，传入oldVal和newVal，进行值的计算，返回的结果最终作为key的value
		map.merge("foo", "boo", (oldVal, newVal) -> newVal + " was " + oldVal);
		System.out.println(map.get("foo"));   // boo was foo
		
	}
	
	/**
	 * Java8 CouncurrentHashMap 新增的特有API -----  ForkJoinPool  、 多核并行框架
	 * 
	 * parallelismThreshold:	
	 * 		the (estimated) number of elements needed for this operation to be executed in parallel
	 * 
	 * 	ConcurrentHashMap has been further enhanced with a couple of new methods to perform parallel operations upon the map.
	 * 
	 *  Java 8 introduces three kinds of parallel operations: 
	 *  	forEach
	 *  	search
	 *  	reduce
	 *  
	 *  All of those methods use a common first argument called parallelismThreshold. 
	 *  This threshold indicates the minimum collection size when the operation should be executed in parallel.
	 *  threshold 表示当Map的size超过多少时，才以并行方式执行对应的操作。比如，threshold设置为500，而map的size=499，那操作将以单线程的方式执行。
	 *  
	 *  强制始终以并行方式执行操作，将threshold设置为1。
	 *  use a threshold of one to always force parallel execution for demonstrating purposes.
	 */
	@Test
	public void testCouncurrentHashMap_forEach() {
		
		// 可通过启动参数修改并行度
		// -Djava.util.concurrent.ForkJoinPool.common.parallelism=5
		System.out.println(ForkJoinPool.getCommonPoolParallelism());
		
		
		// forEach - capable of iterating over the key-value pairs of the map in parallel. 
		map.forEach(1, (key, value) -> {
			System.out.printf("key: %s; value: %s; thread: %s\n", 
					key, 
					value, 
					Thread.currentThread().getName());
		});
		
		System.out.println("\n\n");
		
	}
	
	@Test
	public void testCouncurrentHashMap_search() {
		// search - As soon as a non-null result is returned further processing is suppressed.  更快的查询
		String result = map.search(1, (key, value) -> {
		    System.out.println(Thread.currentThread().getName());
		    if ("foo".equals(key)) {
		        return value;
		    }
		    return null;
		});
		System.out.println("Result: " + result);
		
		
		System.out.println("\n\n");
		
		// searchValues - searching solely on the values of the map:
		result = map.searchValues(1, value -> {
		    System.out.println(Thread.currentThread().getName());
		    if (value.length() > 3) {
		        return value;
		    }
		    return null;
		});

		System.out.println("Result: " + result);
		
		
		
		System.out.println("\n\n");
		
		// 查询value长度大于3的entry
		String kv = map.search(1, (key, value) -> {
		    System.out.println(Thread.currentThread().getName());
		    if (value.length() > 3) {
		        return key + "=" + value;
		    }
		    return null;
		});
		System.out.println("Result: " + kv);
	}
	
	
	/**
	 * reduce - accepts two lambda expressions of type BiFunction.
	 * 	
	 */
	@Test
	public void testCouncurrentHashMap_reduce() {
		
		// The first function transforms each key-value pair into a single value of any type.
		// map each entry to a single value
		BiFunction<String, String, String> transformer = 
				(key, value) -> {
			        System.out.println("Transform: " + Thread.currentThread().getName());
			        return key + "=" + value;
			    };
			    
		// The second function combines all those transformed values into a single result, ignoring any possible null values.
		// accumulating the values
	    BiFunction<String, String, String> reducer = 
	    		(s1, s2) -> {
			        System.out.println("Reduce: " + Thread.currentThread().getName());
			        return s1 + ", " + s2;
			    };
			    
		String result = map.reduce(1, transformer, reducer);

		System.out.println("Result: " + result);
	}
	
}
