package com.asynclife.basic.advanced.construction;

public class Constructors {

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        final NoConstructor noConstructorInstance = new NoConstructor();
        final NoArgConstructor noArgConstructor = new NoArgConstructor();

        final ConstructorWithArguments constructorWithArguments
                = new ConstructorWithArguments("arg1", "arg2");

        final InitializationBlock initializationBlock
                = new InitializationBlock();

        final InitializationBlocks initializationBlocks
                = new InitializationBlocks();

        final InitializationBlockAndConstructor initializationBlockAndConstructor
                = new InitializationBlockAndConstructor();

        final InitializationWithDefaults initializationWithDefaults
                = new InitializationWithDefaults();
    }
}
