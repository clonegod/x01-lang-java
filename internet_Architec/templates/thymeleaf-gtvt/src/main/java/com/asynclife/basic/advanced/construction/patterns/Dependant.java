package com.asynclife.basic.advanced.construction.patterns;

import java.text.DateFormat;
import java.util.Date;

public class Dependant {
    private final DateFormat format;
    
    public Dependant( final DateFormat format ) {
        this.format = format;
    }
    
    public String format( final Date date ) {
        return format.format( date );
    }
}
