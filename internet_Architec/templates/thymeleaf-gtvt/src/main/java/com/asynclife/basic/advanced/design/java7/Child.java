package com.asynclife.basic.advanced.design.java7;

// Resides in the same package as parent class
public class Child extends Parent implements Parent.ProtectedInterface {

    @Override
    protected void protectedAction() {
        // Calls parent's method implementation
        super.protectedAction();
    }

    @Override
    void packageAction() {
        // Do nothing, no call to parent's method implementation
    }

    public void childAction() {
        this.protectedField = "value";
    }
}
