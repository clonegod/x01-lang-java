package com.asynclife.basic.advanced.guidelines;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Vector;

public class CollectionInterfaces {

    public static < T> void print(final Collection< T> collection) {
        for (final T element : collection) {
            System.out.println(element);
        }
    }

    public static void main(String[] args) {
        print(new HashSet< Object>( /* ... */));
        print(new ArrayList< Integer>( /* ... */));
        print(new TreeSet< String>( /* ... */));
        print(new Vector< Long>( /* ... */));
    }
}
