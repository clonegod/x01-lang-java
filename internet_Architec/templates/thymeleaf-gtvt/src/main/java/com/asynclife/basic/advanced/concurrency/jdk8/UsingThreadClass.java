package com.asynclife.basic.advanced.concurrency.jdk8;

public class UsingThreadClass {

    public static void main(String[] args) {
        new Thread(() -> {
            /* Some implementation here */ }).start();
    }
}
