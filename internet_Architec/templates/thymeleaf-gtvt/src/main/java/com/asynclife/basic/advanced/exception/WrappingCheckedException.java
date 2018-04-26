package com.asynclife.basic.advanced.exception;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class WrappingCheckedException {

    public static void main(String[] args) {
        try {
            // Some I/O operation here
            InputStream in = new FileInputStream("");
            in.read();
        } catch (final IOException ex) {
            throw new RuntimeException("I/O operation failed", ex);
        }
    }
}
