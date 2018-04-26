package com.asynclife.basic.advanced.guidelines;

import java.util.Date;

public class StringsFormatting {

    public static void main(String[] args) {
        String.format("%04d", 1);
        String.format("%.2f", 12.324234d);
        String.format("%tR", new Date());
        String.format("%tF", new Date());
        String.format("%d%%", 12);
    }
}
