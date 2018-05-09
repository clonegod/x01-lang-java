package baeldung.synchronizer.Semaphore;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.concurrent.TimedSemaphore;

/**
 * Apache Commons TimedSemaphore. 
 *	在指定时间内，仅允许给定个数的线程获得permit。超时后自动重置。
 */
class DelayQueueUsingTimedSemaphore {

    private final TimedSemaphore semaphore;

    DelayQueueUsingTimedSemaphore(long period, int slotLimit) {
        semaphore = new TimedSemaphore(period, TimeUnit.SECONDS, slotLimit);
    }

    boolean tryAdd() {
        return semaphore.tryAcquire();
    }

    int availableSlots() {
        return semaphore.getAvailablePermits();
    }

}
