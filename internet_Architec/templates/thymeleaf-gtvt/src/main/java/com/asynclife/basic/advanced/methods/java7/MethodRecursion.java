package com.asynclife.basic.advanced.methods.java7;

import java.util.Arrays;

public class MethodRecursion {

    public int sum(int[] numbers) {
        if (numbers.length == 0) {
            return 0;
        }
        if (numbers.length == 1) {
            return numbers[0];
        } else {
            return numbers[0] + sum(Arrays.copyOfRange(numbers, 1, numbers.length));
        }
    }

    public static void main(String[] args) {
        System.out.println(new MethodRecursion().sum(new int[]{2, 5, 6}));
    }
}
