package com.asynclife.basic.advanced.generics.java7;

import java.io.Serializable;

@SuppressWarnings({"serial", "unused"})
public class Example1Generics {

    class SomeClass implements Serializable {
    }

    public < T extends Serializable> T performAction(final T instance) {
        // Do something here
        return instance;
    }

    {
        final SomeClass instance = new SomeClass();
        final SomeClass modifiedInstance = performAction(instance);
    }
}
