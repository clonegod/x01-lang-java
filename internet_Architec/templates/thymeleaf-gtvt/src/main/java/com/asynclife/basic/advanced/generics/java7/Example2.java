package com.asynclife.basic.advanced.generics.java7;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Example2 {

    interface SerializableAndRunnable extends Serializable, Runnable {

    }

    // The class itself should be modified to use the intermediate interface
    // instead of direct implementations
    class SomeClass implements SerializableAndRunnable {

        @Override
        public void run() {
            // Some implementation
        }
    }

    public void performAction(final SerializableAndRunnable instance) {
        // Do something here
    }
}
