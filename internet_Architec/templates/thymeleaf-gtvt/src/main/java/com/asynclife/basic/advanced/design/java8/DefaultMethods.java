package com.asynclife.basic.advanced.design.java8;

public interface DefaultMethods extends Runnable, AutoCloseable {

    @Override
    default void run() {
        // Some implementation here
    }

    @Override
    default void close() throws Exception {
        // Some implementation here
    }
}
