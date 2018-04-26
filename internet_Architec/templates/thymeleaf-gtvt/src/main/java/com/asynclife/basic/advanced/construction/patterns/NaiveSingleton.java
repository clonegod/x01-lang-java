package com.asynclife.basic.advanced.construction.patterns;

public class NaiveSingleton {
    private static NaiveSingleton instance;
    
    private NaiveSingleton() {        
    }
    
    public static NaiveSingleton getInstance() {
        if( instance == null ) {
            instance = new NaiveSingleton();
        }
        
        return instance;
    }
}
