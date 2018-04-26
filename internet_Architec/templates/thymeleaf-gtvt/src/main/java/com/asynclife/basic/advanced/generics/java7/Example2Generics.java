package com.asynclife.basic.advanced.generics.java7;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Example2Generics {

    class SomeClass implements Serializable, Runnable {

        @Override
        public void run() {
        }
    }

    public < T extends Serializable & Runnable> void performAction(final T instance) {
        // Do something here
    }
}
