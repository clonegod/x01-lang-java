package com.asynclife.basic.advanced.construction.patterns;

public class BookFactory {
    private BookFactory( final String title) {
    }     

    public static BookFactory newBook( final String title ) { 
        return new BookFactory( title );
    }
}
