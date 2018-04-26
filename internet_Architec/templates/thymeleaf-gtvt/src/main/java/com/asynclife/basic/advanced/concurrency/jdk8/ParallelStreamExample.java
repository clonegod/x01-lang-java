package com.asynclife.basic.advanced.concurrency.jdk8;

import java.util.ArrayList;
import java.util.Collection;

public class ParallelStreamExample {

    public static void main(String[] args) {
        final Collection< String> strings = new ArrayList<>();
        // Some implementation here

        final int sumOfLengths = strings.parallelStream()
                .filter(str -> !str.isEmpty())
                .mapToInt(str -> str.length())
                .sum();
    }
}
