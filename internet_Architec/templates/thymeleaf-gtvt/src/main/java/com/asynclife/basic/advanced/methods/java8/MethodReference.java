package com.asynclife.basic.advanced.methods.java8;

import java.util.Arrays;
import java.util.Collection;

public class MethodReference {

    public static void println(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
        final Collection< String> strings = Arrays.asList("s1", "s2", "s3");
        strings.stream().forEach(MethodReference::println);
    }
}
