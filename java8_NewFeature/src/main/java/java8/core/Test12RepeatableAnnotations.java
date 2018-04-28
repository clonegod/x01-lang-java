package java8.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Test12RepeatableAnnotations {
	/**
	 * Annotations in Java 8 are repeatable.
	 *
	 */
	
	// a wrapper annotation which holds an array of the actual annotations
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@interface Hints {
		Hint[] value();
	}
	
	
	// Java 8 enables us to use multiple annotations of the same type by declaring the annotation @Repeatable.
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Repeatable(Hints.class)
	@interface Hint {
		String value();
	}
	
	
//	@Hints({@Hint("hint1"), @Hint("hint2")})
//	class Person {}

	
	@Hint("hint1")
	@Hint("hint2")
	class Person {}
	
	
	public static void main(String[] args) {
		Hint hint = Person.class.getAnnotation(Hint.class);
		System.out.println(hint);                   // null

		Hints hints1 = Person.class.getAnnotation(Hints.class);
		System.out.println(hints1.value().length);  // 2

		Hint[] hints2 = Person.class.getAnnotationsByType(Hint.class);
		System.out.println(hints2.length);          // 2
	}
	
}


