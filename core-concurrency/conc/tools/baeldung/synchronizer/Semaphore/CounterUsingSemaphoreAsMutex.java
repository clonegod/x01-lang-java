package baeldung.synchronizer.Semaphore;

import java.util.concurrent.Semaphore;

/**
 * 
 *	Semaphore 当做互斥锁使用
 */
class CounterUsingSemaphoreAsMutex {

    private final Semaphore mutex;
    private int count;

    CounterUsingSemaphoreAsMutex() {
        mutex = new Semaphore(1); // 仅构造1个permit，因此实现了多线程互斥访问共享资源
        count = 0;
    }

    void increase() throws InterruptedException {
        mutex.acquire();
        this.count = this.count + 1;
        Thread.sleep(1000);
        mutex.release();

    }

    int getCount() {
        return this.count;
    }

    boolean hasQueuedThreads() {
        return mutex.hasQueuedThreads();
    }

}
