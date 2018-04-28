package java8.core;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

public class Test09ParallelStreams {
	
	/**
	 * Parallel Streams
	 * 		streams can be either sequential or parallel. 
	 * 		Operations on sequential streams are performed on a single thread 
	 * 		while operations on parallel streams are performed concurrent on multiple threads.
	 * 
	 *  注意：
	 *  	并行流计算，只有在数据量很大的前提下，才有可能提升效率！
	 *  	不建议在数据量小的情况下使用parallel stream处理数据！
	 */
	
	int max = 1_000_0000;
	List<String> values = new ArrayList<>(max);
	
	@Before
	public void init() {
		for (int i = 0; i < max; i++) {
			UUID uuid = UUID.randomUUID();
			values.add(uuid.toString());
		}
		
	}

	@Test
	public void testSequentialSort() {
		long t0 = System.nanoTime();

		long count = values.stream().sorted().count();
		System.out.println(count);

		long t1 = System.nanoTime();

		long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
		System.out.println(String.format("sequential sort took: %d ms", millis));
		
		// 664 ms
	}
	
	
	@Test
	public void testParallelSort() {
		long t0 = System.nanoTime();

		long count = values.parallelStream().sorted().count();
		System.out.println(count);

		long t1 = System.nanoTime();

		long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
		System.out.println(String.format("parallel sort took: %d ms", millis));

		// parallel sort took: 340 ms
	}
	
	
}
