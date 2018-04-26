package com.asynclife.basic.advanced.design.java7;

public interface InterfaceWithDefinitions {

    String CONSTANT = "CONSTANT";

    enum InnerEnum {
        E1, E2;
    }

    class InnerClass {
    }

    interface InnerInterface {

        void performInnerAction();
    }

    void performAction();
}
