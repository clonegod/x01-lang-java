package com.javacodegeeks.advanced.objects;

import java.util.Objects;


/**
 * Whenever possible, try use final fields while implementing equals and hashCode. 
 * It will guarantee that behavior of those methods will not be affected by field changes. 
 * However, in real-world projects it is not always possible.
 *    
 * Make sure that the same fields are used within implementation of equals() and hashCode(). 
 * It will guarantee consistent behavior of both methods in case of any change affecting the 
 * fields in question.      
 */
public class PersonObjects {
    private final String firstName;
    private final String lastName;
    private final String email;
    
    public PersonObjects( final String firstName, final String lastName, final String email ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }

    // Step 0: Please add the @Override annotation, it will ensure that your
    // intention is to change the default implementation.
    @Override
    public boolean equals( Object obj ) {
        // Step 1: Check if the 'obj' is null
        if ( obj == null ) {
            return false;
        }
        
        // Step 2: Check if the 'obj' is pointing to the this instance
        if ( this == obj ) {
            return true;
        }
        
        // Step 3: Check classes equality. Note of caution here: please do not use the 
        // 'instanceof' operator unless class is declared as final. It make cause an issues
        // within class hierarchies.
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        
        // Step 4: Check individual fields equality
        final PersonObjects other = (PersonObjects) obj;
        if( !Objects.equals( email, other.email ) ) {
            return false;
        } else if( !Objects.equals( firstName, other.firstName ) ) {
            return false;            
        } else if( !Objects.equals( lastName, other.lastName ) ) {
            return false;            
        }
        
        return true;
    }
        
    @Override
    public int hashCode() {
        return Objects.hash( email, firstName, lastName );
    }      
}
