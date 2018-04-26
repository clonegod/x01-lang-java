package com.asynclife.basic.advanced.generics.java7;

import java.io.Serializable;

@SuppressWarnings({"serial", "unused"})
public class Example1 {

    class SomeClass implements Serializable {
    }

    public Serializable performAction(final Serializable instance) {
        // Do something here
        return instance;
    }

    {
        final SomeClass instance = new SomeClass();
        // Please notice a necessary type cast required
        final SomeClass modifiedInstance = (SomeClass) performAction(instance);
    }
}
