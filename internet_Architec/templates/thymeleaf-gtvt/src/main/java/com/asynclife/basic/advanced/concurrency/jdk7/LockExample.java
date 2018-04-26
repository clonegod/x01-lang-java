package com.asynclife.basic.advanced.concurrency.jdk7;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample {

    private final ReentrantLock lock = new ReentrantLock();

    public void performAction() {
        lock.lock();

        try {
            // Some implementation here
        } finally {
            lock.unlock();
        }
    }

    public void performActionWithTimeout() throws InterruptedException {
        if (lock.tryLock(1, TimeUnit.SECONDS)) {
            try {
                // Some implementation here
            } finally {
                lock.unlock();
            }
        }
    }
}
