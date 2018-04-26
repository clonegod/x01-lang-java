package com.asynclife.basic.advanced.design.java7;

public class MultipleInterfaces implements Runnable, AutoCloseable {

    @Override
    public void run() {
        // Some implementation here
    }

    @Override
    public void close() throws Exception {
        // Some implementation here
    }
}
