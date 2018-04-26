package com.asynclife.basic.advanced.concurrency.jdk7;

public class SynchronizedKeyword {

    public synchronized void performAction() {
        // Some implementation here
    }

    public static synchronized void performClassAction() {
        // Some implementation here
    }

    public void performActionBlock() {
        synchronized (this) {
            // Some implementation here
        }
    }
}
