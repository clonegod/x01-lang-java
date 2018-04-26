package com.javacodegeeks.advanced.objects;

public class OfficeToString {
    public static void main(String[] args) {
        final Person person = new Person( "John", "Smith", "john.smith@domain.com" );
        final Office office = new Office( person );
        System.out.println( office.toString() );
    }
}
