package com.asynclife.basic.advanced.design.java8;

public class DiamondOfDeath {

    interface A {

        default void performAction() {
        }
    }

    interface B extends A {

        @Override
        default void performAction() {
        }
    }

    interface C extends A {

        @Override
        default void performAction() {
        }
    }

    // E is not compilable unless it overrides perfromAction() as well
    interface E extends B, C {

        @Override
        default void performAction() {
            System.out.println("must overrides perfromAction() as well");
        }
    }
}
