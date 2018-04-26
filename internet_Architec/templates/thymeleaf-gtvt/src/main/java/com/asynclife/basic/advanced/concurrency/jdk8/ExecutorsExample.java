package com.asynclife.basic.advanced.concurrency.jdk8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorsExample {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        executor.execute(() -> {
            // Some implementation here
        });
    }
}
