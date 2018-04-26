package com.asynclife.basic.advanced.enums_annotation.jdk7.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
public @interface AnnotationWithTarget {
}
