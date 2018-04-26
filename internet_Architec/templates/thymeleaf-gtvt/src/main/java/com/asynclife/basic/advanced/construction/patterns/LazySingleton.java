package com.asynclife.basic.advanced.construction.patterns;

public class LazySingleton {
    private static LazySingleton instance;
    
    private LazySingleton() {        
    }
    
    public static synchronized LazySingleton getInstance() {
        if( instance == null ) {
            instance = new LazySingleton();
        }
        
        return instance;
    }
}
