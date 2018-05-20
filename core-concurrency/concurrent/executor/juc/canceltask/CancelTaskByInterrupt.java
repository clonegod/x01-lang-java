package juc.canceltask;

import java.math.BigInteger;
import java.util.concurrent.*;

/**
 * PrimeProducer
 * <p/>
 * Using interruption for cancellation
 *
 * @author Brian Goetz and Tim Peierls
 */
public class CancelTaskByInterrupt extends Thread {
    private final BlockingQueue<BigInteger> queue;

    CancelTaskByInterrupt(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!Thread.currentThread().isInterrupted())
                queue.put(p = p.nextProbablePrime());
        } catch (InterruptedException consumed) {
            /* Allow thread to exit */
        	// 中断的处理方式：退出任务
        }
    }

    /**
     * 以中断的方式来取消任务
     */
    public void cancel() {
        interrupt();
    }
}
