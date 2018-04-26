package com.asynclife.basic.advanced.concurrency.jdk7;

public class UsingThreadClass {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Some implementation here
            }
        }).start();
    }
}
