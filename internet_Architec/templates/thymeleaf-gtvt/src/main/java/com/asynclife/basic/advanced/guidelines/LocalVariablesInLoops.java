package com.asynclife.basic.advanced.guidelines;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class LocalVariablesInLoops {

    @SuppressWarnings("unused")
    public static void main(String[] args) throws FileNotFoundException, IOException {
        try (final InputStream in = new FileInputStream("file.txt")) {
            // Some implementation here
        }

        for (final Locale locale : Locale.getAvailableLocales()) {
            // Some implementation here
        }
    }
}
