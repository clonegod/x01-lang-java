package com.asynclife.basic.advanced.enums_annotation.jdk8.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class RepeatableAnnotationsExample {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface RepeatableAnnotations {

        RepeatableAnnotation[] value();
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Repeatable(RepeatableAnnotations.class)
    public @interface RepeatableAnnotation {

        String value();
    };

    @RepeatableAnnotation("repeatition 1")
    @RepeatableAnnotation("repeatition 2")
    public void performAction() {
        // Some code here
    }
}
