package com.javacodegeeks.advanced.objects;

import java.util.Arrays;

public class Office implements Cloneable {
    private Person[] persons;

    public Office( Person ... persons ) {
         this.persons = Arrays.copyOf( persons, persons.length );
    }

    @Override
    public Office clone() throws CloneNotSupportedException {
        final Office clone = ( Office )super.clone();
        clone.persons = persons.clone();
        return clone;
    }
    
    @Override
    public String toString() {
        return String.format( "%s{persons=%s}", 
            getClass().getSimpleName(), Arrays.toString( persons ) );
    }
    
    public Person[] getPersons() {
        return persons;
    }
}