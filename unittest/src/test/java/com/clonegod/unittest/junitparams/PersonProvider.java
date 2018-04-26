package com.clonegod.unittest.junitparams;

/**
 * PersonProvider class must have at least one static method that starts with provide and returns Object[]
 *
 */
public class PersonProvider {
    public static Object[] provideAdults() {
        return new Object[]{
                 new Object[]{new Person(25), true},
                 new Object[]{new Person(32), true}
               };
    }

    public static Object[] provideTeens() {
        return new Object[]{
                 new Object[]{new Person(12), false},
                 new Object[]{new Person(17), false}
               };
    }
}