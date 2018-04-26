package com.asynclife.basic.advanced.guidelines;

public class MethodArgumentAsLocalVariable {

    public String sanitize(String str) {
        if (!str.isEmpty()) {
            str = str.trim();
        }

        str = str.toLowerCase();
        return str;
    }

    public String sanitize2(final String str) {
        String sanitized = str;

        if (!str.isEmpty()) {
            sanitized = str.trim();
        }

        sanitized = sanitized.toLowerCase();
        return sanitized;
    }
}
