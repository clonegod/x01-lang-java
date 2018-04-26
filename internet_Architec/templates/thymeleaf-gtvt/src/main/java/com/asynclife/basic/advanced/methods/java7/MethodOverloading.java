package com.asynclife.basic.advanced.methods.java7;

import java.math.BigDecimal;

public class MethodOverloading {

    public String numberToString(Long number) {
        return Long.toString(number);
    }

    public String numberToString(BigDecimal number) {
        return number.toString();
    }
}
