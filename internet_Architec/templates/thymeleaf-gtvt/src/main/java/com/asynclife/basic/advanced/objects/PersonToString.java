package com.javacodegeeks.advanced.objects;

public class PersonToString {
    public static void main(String[] args) {
        final Person person = new Person( "John", "Smith", "john.smith@domain.com" );
        System.out.println( person.toString() );
    }
}
