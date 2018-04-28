package java8.core;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.junit.Test;

import com.google.common.base.Stopwatch;

/**
 * >>> Stream 
 * the concept of stream that lets the developer to process data declaratively 声明式处理数据
 * and leverage multicore architecture without the need to write any specific code for it. 多核并行计算
 * 
 * >>> Generating Streams：
 * With Java 8, Collection interface has two methods to generate a Stream.
 * 	stream()
 *  parallelStream()
 * 
 * >>> Feature：
 * 	Source − Stream takes Collections, Arrays, or I/O resources as input source.
 * 
 * 	Automatic iterations − Stream operations do the iterations internally over the source elements provided, in contrast to Collections where explicit iteration is required.
 * 
 *  Sequence of elements − A stream provides a set of elements of specific type in a sequential manner. 
 *  						A stream gets/computes elements on demand. It never stores the elements.
 *  
 *  Pipelining − Most of the stream operations return stream itself so that their result can be pipelined. 
 *  			比如，将结果通过管道传递给下一个命令处理：grep "AppException" app.log | grep "idcard" | less
 *  		These operations are called intermediate operations and their function is to take input, process them, and return output to the target. 
 *  		collect() method is a terminal operation which is normally present at the end of the pipelining operation to mark the end of the stream.
 */
public class Test08StreamAPI {
	
	// Stream has provided a new method ‘forEach’ to iterate each element of the stream.
	@Test
	public void testForeach() {
		Random random = ThreadLocalRandom.current();
		random.ints().limit(10).forEach(System.out::println);
	}
	
	// The ‘map’ method is used to map each element to its corresponding result. 
	@Test
	public void testMap() {
		List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

		//get list of unique squares
		List<Integer> squaresList = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());
		squaresList.forEach(System.out::println);
	}
	
	// The ‘filter’ method is used to eliminate elements based on a criteria.
	@Test
	public void testFilter() {
		List<String>strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");

		//get count of empty string
		long count = strings.stream().filter(string -> string.isEmpty()).count();
		System.out.println(count);
	}
	
	// The ‘limit’ method is used to reduce the size of the stream.
	@Test
	public void testLimit() {
		Random random = new Random();
		random.ints().limit(10).forEach(System.out::println);
	}
	
	// The ‘sorted’ method is used to sort the stream.
	@Test
	public void testSort() {
		Random random = new Random();
		random.ints().limit(10).map(n -> Math.abs(n)).sorted().forEach(System.out::println);
	}
	
	// Collectors are used to combine the result of processing on the elements of a stream.
	@Test
	public void testCollector() {
		List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
		List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());

		System.out.println("Filtered List: " + filtered);
		String mergedString = strings.stream().sorted().collect(Collectors.joining("; "));
		System.out.println("Merged String: " + mergedString);
	}
	
	// calculate all statistics when stream processing is being done
	@Test
	public void testStatistics() {
		List<Integer> integers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

		IntSummaryStatistics stats = integers.stream().mapToInt((x) -> x).summaryStatistics();

		System.out.println("Highest number in List : " + stats.getMax());
		System.out.println("Lowest number in List : " + stats.getMin());
		System.out.println("Sum of all numbers : " + stats.getSum());
		System.out.println("Average of all numbers : " + stats.getAverage());
	}
	
	// --------------------------------
	
	@Test
	public void testStreams() {
		String[] strings = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
		
		Stopwatch sw = Stopwatch.createStarted();
        System.out.println("-------\nRunning sequential\n-------");
        run(Arrays.stream(strings).sequential());
        sw.stop();
        System.out.println("takes:" + sw.elapsed(TimeUnit.MILLISECONDS));
        
        sw = Stopwatch.createStarted();
        System.out.println("-------\nRunning parallel\n-------");
        run(Arrays.stream(strings).parallel());
        sw.stop();
        System.out.println("takes:" + sw.elapsed(TimeUnit.MILLISECONDS));
        
	}
	
	  public static void run (Stream<String> stream) {
	        stream.forEach(s -> {
	            System.out.println(LocalTime.now() + " - value: 抓取第" + s + "月数据" + 
	                                " - thread: " + Thread.currentThread().getName());
	            try {
	                Thread.sleep(200);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        });
	    }
	

	long count = 10 * 10;
//	long count = 10000 * 5000; 
	
	
	@Test
	public void testStream() throws IOException {
		Stopwatch sw = Stopwatch.createStarted();
		LongStream stream = ThreadLocalRandom.current().ints().limit(count).asLongStream();
		System.out.println(stream.isParallel());
		stream.sorted().count();
		sw.stop();
		
		System.out.println("takes: "+sw.elapsed(TimeUnit.MILLISECONDS));
	}
	
	
	/**
	 * The Stream API was designed to make it easy to write computations in a way that was abstracted away from how they would be executed, making switching between sequential and parallel easy.
	 * 
	 * However, just because its easy, doesn't mean its always a good idea, and in fact, it is a bad idea to just drop .parallel() all over the place simply because you can.
	 * 
	 * In any case, measure, don't guess! Only a measurement will tell you if the parallelism is worth it or not.
	 */
	@Test
	public void testParalleStream() throws IOException {
		System.out.println("core="+Runtime.getRuntime().availableProcessors());
		
		Stopwatch sw = Stopwatch.createStarted();
		LongStream stream = ThreadLocalRandom.current().ints().limit(count).asLongStream().parallel();
		System.out.println(stream.isParallel());
		stream.sorted().count();
		sw.stop();
		
		// 可并行，且量非常大，if you have massive amount of items to process, this may works for you!
		System.out.println("takes: "+sw.elapsed(TimeUnit.MILLISECONDS));
	}
	
}
