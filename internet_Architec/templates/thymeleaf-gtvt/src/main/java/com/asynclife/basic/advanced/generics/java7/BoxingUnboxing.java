package com.asynclife.basic.advanced.generics.java7;

import java.util.ArrayList;
import java.util.List;

public class BoxingUnboxing {

    public static void main(String[] args) {
        final List< Long> longs = new ArrayList<>();
        longs.add(0L); // long is boxed to Long

        long value = longs.get(0); // Long is unboxed to long
        System.out.println(value);
        // Do something with value
    }
}
