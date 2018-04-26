package com.asynclife.basic.advanced.methods.java7;

import java.util.Arrays;

public class MethodTailRecursion {

    public int sum(int initial, int[] numbers) {
        if (numbers.length == 0) {
            return initial;
        }
        if (numbers.length == 1) {
            return initial + numbers[0];
        } else {
            return sum(initial + numbers[0],
                    Arrays.copyOfRange(numbers, 1, numbers.length));
        }
    }

    public static void main(String[] args) {
        System.out.println(new MethodTailRecursion().sum(0, new int[]{2, 5, 6}));
    }
}
