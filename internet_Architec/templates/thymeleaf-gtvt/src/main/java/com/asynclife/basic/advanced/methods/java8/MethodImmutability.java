package com.asynclife.basic.advanced.methods.java8;

import java.time.LocalDateTime;

@SuppressWarnings("unused")
public class MethodImmutability {

    public static void main(String[] args) {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime tomorrow = now.plusHours(24);

        final LocalDateTime midnight = now
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }
}
