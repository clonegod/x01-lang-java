package com.javacodegeeks.advanced.objects;

public class Equality {
    public static void main(String[] args) {
        final String str1 = new String( "bbb" );
        System.out.println( "Using == operator: " + ( str1 == "bbb" ) );
        System.out.println( "Using equals() method: " + str1.equals( "bbb" ) );
    }
}
