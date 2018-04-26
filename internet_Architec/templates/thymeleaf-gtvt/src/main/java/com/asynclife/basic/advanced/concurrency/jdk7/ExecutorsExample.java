package com.asynclife.basic.advanced.concurrency.jdk7;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ExecutorsExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Some implementation here
            }
        });

        Future< Long> result = executor.submit(new Callable< Long>() {
            @Override
            public Long call() throws Exception {
                // Some implementation here
                return null;
            }
        });

        Long value = result.get(1, TimeUnit.SECONDS);
    }
}
