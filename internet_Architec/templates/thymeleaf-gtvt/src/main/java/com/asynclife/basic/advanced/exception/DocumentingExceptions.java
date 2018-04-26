package com.asynclife.basic.advanced.exception;

import java.io.IOException;

public class DocumentingExceptions {

    /**
     * Reads file from the file system.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void readFile() throws IOException {
        // Some implementation here
    }

    /**
     * Parses the string representation of some concept.
     *
     * @param str String to parse
     * @throws IllegalArgumentException if the specified string cannot be parsed
     * properly
     * @throws NullPointerException if the specified string is null
     */
    public void parse(final String str) {
        // Some implementation here
    }
}
