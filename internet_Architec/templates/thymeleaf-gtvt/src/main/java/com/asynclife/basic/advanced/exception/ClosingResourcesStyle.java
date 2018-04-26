package com.asynclife.basic.advanced.exception;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ClosingResourcesStyle {

    public void readFile(final File file) {
        try (InputStream in = new FileInputStream(file)) {
            // Some implementation here
        } catch (final IOException ex) {
            // Some implementation here
        }
    }
}
