package baeldung.synchronizer.CyclicBarrier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample2 {

    private CyclicBarrier cyclicBarrier;
    private Random random = new Random();
    private int NUM_PARTIAL_RESULTS_PER_THREAD;
    private int NUM_WORKERS;

    // 多线程把结果写入同一个ArrayList，因此使用同步包装器对象。
    private List<List<Integer>> partialResults = Collections.synchronizedList(new ArrayList<>());

    private void runSimulation(int numWorkers, int numberOfPartialResults) {
        NUM_PARTIAL_RESULTS_PER_THREAD = numberOfPartialResults;
        NUM_WORKERS = numWorkers;
        
        Runnable barrierAction = new AggregatorThread();
        cyclicBarrier = new CyclicBarrier(NUM_WORKERS, barrierAction);
        
        System.out.println("Spawning " + NUM_WORKERS + " worker threads to compute " + NUM_PARTIAL_RESULTS_PER_THREAD + " partial results each");
        for (int i = 0; i < NUM_WORKERS; i++) {
            Thread worker = new Thread(new NumberCruncherThread());
            worker.setName("Thread " + i);
            worker.start();
        }
    }

    class NumberCruncherThread implements Runnable {

        @Override
        public void run() {
            String thisThreadName = Thread.currentThread().getName();
            List<Integer> partialResult = new ArrayList<>();
            for (int i = 0; i < NUM_PARTIAL_RESULTS_PER_THREAD; i++) {
                Integer num = random.nextInt(10);
                System.out.println(thisThreadName + ": Crunching some numbers! Final result - " + num);
                partialResult.add(num);
            }
            partialResults.add(partialResult);
            try {
                System.out.println(thisThreadName + " waiting for others to reach barrier.");
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *	当所有线程都到达barrier后，由最后达到的线程执行的一个任务。
     *	通常是汇集所有线程的执行结果。
     */
    class AggregatorThread implements Runnable {

        @Override
        public void run() {
            String thisThreadName = Thread.currentThread().getName();
            System.out.println(thisThreadName + ": Computing final sum of " + NUM_WORKERS + " workers, having " + NUM_PARTIAL_RESULTS_PER_THREAD + " results each.");
            int sum = 0;
            for (List<Integer> threadResult : partialResults) {
                System.out.print("Adding ");
                for (Integer partialResult : threadResult) {
                    System.out.print(partialResult + " ");
                    sum += partialResult;
                }
                System.out.println();
            }
            System.out.println(Thread.currentThread().getName() + ": Final result = " + sum);
        }

    }

    public static void main(String[] args) {
        CyclicBarrierExample2 play = new CyclicBarrierExample2();
        play.runSimulation(5, 3);
    }

}
