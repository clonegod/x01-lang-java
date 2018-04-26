package com.asynclife.basic.advanced.guidelines;

import java.util.Random;

@SuppressWarnings("unused")
public class Strings {

    public static void main(String[] args) {
        final StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= 10; ++i) {
            sb.append(" ");
            sb.append(i);
        }

        sb.deleteCharAt(0);
        sb.insert(0, "[");
        sb.replace(sb.length() - 3, sb.length(), "]");

        String userId = "user:" + new Random().nextInt(100);
    }
}
