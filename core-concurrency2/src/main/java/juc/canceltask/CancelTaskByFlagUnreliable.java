package juc.canceltask;

import java.math.BigInteger;
import java.util.concurrent.*;

/**
 * BrokenPrimeProducer
 * <p/>
 * Unreliable cancellation that can leave producers stuck in a blocking operation
 *
 * @author Brian Goetz and Tim Peierls
 */
class CancelTaskByFlagUnreliable extends Thread {
    private final BlockingQueue<BigInteger> queue;
    
    private volatile boolean cancelled = false; /**对阻塞方法通过标记来取消任务是不可靠的*/

    CancelTaskByFlagUnreliable(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!cancelled)
                queue.put(p = p.nextProbablePrime()); // 阻塞
        } catch (InterruptedException consumed) {
        }
    }

    public void cancel() {
        cancelled = true; // 当线程处于阻塞状态时，是无法检测到cancel标记的！
    }
}

