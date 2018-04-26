package com.asynclife.basic.advanced.guidelines;

public class LocalVariableAndClassMember {

    private long value;

    public long calculateValue(final long initial) {
        long value = initial;

        value *= 10;
        value += this.value;

        return value;
    }
}
