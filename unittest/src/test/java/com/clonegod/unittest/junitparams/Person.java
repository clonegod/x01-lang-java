package com.clonegod.unittest.junitparams;

public class Person {
    private String name;
    private int age;

    public Person(Integer age) {
        this.age = age;
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public boolean isAdult() {
        return age >= 18;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person of age: " + age;
    }
}