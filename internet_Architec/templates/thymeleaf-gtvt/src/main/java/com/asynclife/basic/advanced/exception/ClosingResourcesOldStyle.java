package com.asynclife.basic.advanced.exception;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ClosingResourcesOldStyle {

    public void readFile(final File file) {
        InputStream in = null;

        try {
            in = new FileInputStream(file);
            // Some implementation here
        } catch (IOException ex) {
            // Some implementation here
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (final IOException ex) {
                    /* do nothing */
                }
            }
        }
    }
}
