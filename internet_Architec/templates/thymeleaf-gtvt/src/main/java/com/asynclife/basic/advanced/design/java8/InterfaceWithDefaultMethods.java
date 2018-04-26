package com.asynclife.basic.advanced.design.java8;

public interface InterfaceWithDefaultMethods {

    void performAction();

    default void performDefaulAction() {
        // Implementation here
    }

    static void createAction() {
        // Implementation here
    }
}
