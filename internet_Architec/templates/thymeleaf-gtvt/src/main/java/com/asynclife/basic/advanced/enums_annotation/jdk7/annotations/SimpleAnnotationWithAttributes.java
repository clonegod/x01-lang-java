package com.asynclife.basic.advanced.enums_annotation.jdk7.annotations;

public @interface SimpleAnnotationWithAttributes {

    String name();

    int order() default 0;
}
