package com.asynclife.basic.advanced.concurrency.jdk7;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SynchronizedCollections {

    public static void main(String[] args) {
        final Set< String> strings = Collections.synchronizedSet(
                new HashSet< String>());

        final Map< String, String> keys = Collections.synchronizedMap(
                new HashMap< String, String>());
    }
}
