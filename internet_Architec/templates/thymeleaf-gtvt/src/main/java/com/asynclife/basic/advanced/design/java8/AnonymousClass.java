package com.asynclife.basic.advanced.design.java8;

public class AnonymousClass {

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("this is a Lambda - function interface");
            /* Implementation here */ }).start();
    }
}
