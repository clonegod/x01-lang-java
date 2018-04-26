package com.asynclife.basic.advanced.generics.java7;

public class GenericClassImplementingGenericInterface< T> implements GenericInterfaceOneType< T> {

    @Override
    public void performAction(final T action) {
        // Implementation here
    }
}
