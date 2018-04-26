package baeldung.queue.DelayQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DelayQueueTest {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		BlockingQueue<DelayObject> queue = new DelayQueue<>();
		
		int numberOfElementsToProduce = 2;
		int delayOfEachProducedMessageInMilliseconds = 500;
		
		DelayQueueConsumer consumer = new DelayQueueConsumer(
				queue, numberOfElementsToProduce);
		
		DelayQueueProducer producer = new DelayQueueProducer(
				queue, numberOfElementsToProduce, delayOfEachProducedMessageInMilliseconds);
		
		executor.submit(producer);
		executor.submit(consumer);
		
		executor.awaitTermination(5, TimeUnit.SECONDS);
		executor.shutdown();
		
	}
}
