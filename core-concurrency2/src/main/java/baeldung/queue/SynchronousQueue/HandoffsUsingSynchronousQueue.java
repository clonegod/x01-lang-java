package baeldung.queue.SynchronousQueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class HandoffsUsingSynchronousQueue {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		SynchronousQueue<Integer> queue = new SynchronousQueue<>();
		
		Runnable producer = () -> {
		    Integer producedElement = ThreadLocalRandom
		      .current()
		      .nextInt();
		    try {
		        queue.put(producedElement);
		        System.out.println("Saving an element: " + producedElement + " to the exchange point");
		    } catch (InterruptedException ex) {
		        ex.printStackTrace();
		    }
		};
		
		Runnable consumer = () -> {
		    try {
		        Integer consumedElement = queue.take();
		        System.out.println("Consumed an element: " + consumedElement + " from the exchange point");
		    } catch (InterruptedException ex) {
		        ex.printStackTrace();
		    }
		};
		
		executor.execute(producer);
		executor.execute(consumer);
		 
		executor.awaitTermination(500, TimeUnit.MILLISECONDS);
		executor.shutdown();
	}
}
