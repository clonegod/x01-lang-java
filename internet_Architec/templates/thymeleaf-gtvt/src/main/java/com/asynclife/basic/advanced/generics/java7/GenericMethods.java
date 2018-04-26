package com.asynclife.basic.advanced.generics.java7;

public class GenericMethods< T> {

    public GenericMethods(final T initialAction) {
        // Implementation here
    }

    public < J> GenericMethods(final T initialAction, final J nextAction) {
        // Implementation here
    }

    public < R> R performAction(final T action) {
        final R result = null;
        // Implementation here
        return result;
    }

    public < U, R> R performAnotherAction(final U action) {
        final R result = null;
        // Implementation here
        return result;
    }
}
