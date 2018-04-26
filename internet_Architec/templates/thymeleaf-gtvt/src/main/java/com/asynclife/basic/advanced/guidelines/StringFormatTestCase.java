package com.asynclife.basic.advanced.guidelines;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class StringFormatTestCase {

    @Test
    public void testNumberFormattingWithLeadingZeros() {
        final String formatted = String.format("%04d", 1);
        assertThat(formatted, equalTo("0001"));
    }

    @Test
    public void testDoubleFormattingWithTwoDecimalPoints() {
        final String formatted = String.format("%.2f", 12.325234d);
        assertThat(formatted, equalTo("12.32"));
    }
}
