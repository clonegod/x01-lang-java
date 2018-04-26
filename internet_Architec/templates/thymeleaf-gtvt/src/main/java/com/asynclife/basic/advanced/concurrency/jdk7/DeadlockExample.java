package com.asynclife.basic.advanced.concurrency.jdk7;

import java.util.concurrent.locks.ReentrantLock;

public class DeadlockExample {

    private final ReentrantLock lock1 = new ReentrantLock();
    private final ReentrantLock lock2 = new ReentrantLock();

    public void performAction() {
        lock1.lock();

        try {
            // Some implementation here
            try {
                lock2.lock();
                // Some implementation here
            } finally {
                lock2.unlock();
            }
            // Some implementation here
        } finally {
            lock1.unlock();
        }
    }

    public void performAnotherAction() {
        lock2.lock();

        try {
            // Some implementation here
            try {
                lock1.lock();
                // Some implementation here
            } finally {
                lock1.unlock();
            }
            // Some implementation here
        } finally {
            lock2.unlock();
        }
    }
}
