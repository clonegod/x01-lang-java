package com.javacodegeeks.advanced.objects;

public class PersonClone {
    public static void main(String[] args) throws CloneNotSupportedException {
        final Person person = new Person( "John", "Smith", "john.smith@domain.com" );
        System.out.println( person.clone().toString() );
    }
}
