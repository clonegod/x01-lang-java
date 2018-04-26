package com.javacodegeeks.advanced.objects;

public class OfficeClone {
    public static void main(String[] args) throws CloneNotSupportedException {
        final Person person = new Person( "John", "Smith", "john.smith@domain.com" );
        final Office office = new Office( person );
        System.out.println( office.clone().toString() );
    }
}
