package com.asynclife.basic.advanced.design.java7;

public class Parent {

    // Everyone can see it
    public static final String CONSTANT = "Constant";

    // No one can access it
    private String privateField;
    // Only subclasses can access it
    protected String protectedField;

    // No one can see it
    private class PrivateClass {
    }

    // Only visible to subclasses
    protected interface ProtectedInterface {
    }

    // Everyone can call it
    public void publicAction() {
    }

    // Only subclass can call it
    protected void protectedAction() {
    }

    // No one can call it
    private void privateAction() {
    }

    // Only subclasses in the same package can call it
    void packageAction() {
    }
}
