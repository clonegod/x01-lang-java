package com.asynclife.basic.advanced.methods.java7;

public class MethodOverloadingVsOverriding {

    public class Parent {

        public Object toObject(Number number) {
            return number.toString();
        }
    }

    public class Child extends Parent {

        @Override
        public String toObject(Number number) {
            return number.toString();
        }

        public String toObject(Double number) {
            return number.toString();
        }
    }
}
