package com.asynclife.basic.advanced.construction.patterns;

public class EagerSingleton {
    private static final EagerSingleton instance = new EagerSingleton();
    
    private EagerSingleton() {        
    }
    
    public static EagerSingleton getInstance() {
        return instance;
    }
}
