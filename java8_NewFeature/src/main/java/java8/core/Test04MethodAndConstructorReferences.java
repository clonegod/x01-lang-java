package java8.core;

import org.junit.Test;

import com.google.common.base.MoreObjects;

public class Test04MethodAndConstructorReferences {

	@FunctionalInterface
	interface Converter<F, T> {
	    T convert(F from);
	    
	    default Integer convert2(String from) {
	    	return Integer.parseInt(from.concat("00"));
	    }
	}
	
	/**
	 * Java 8 enables you to pass references of methods or constructors via the :: keyword
	 * 
	 */
	
	@Test
	// references static method 
	public void test01() {
		Converter<String, Integer> converter = Integer::valueOf; 
		Integer converted = converter.convert("123");
		System.out.println(converted);   // 123
		
		System.out.println(converter.convert2("123"));
	}

	
	
	
	
	static class Something {
		String startsWith(String s) {
			return String.valueOf(s.charAt(0));
		}
	}
	
	//  reference object method
	@Test
	public void test02() {
		Converter<String, String> converter2 = new Something()::startsWith;
		String converted2 = converter2.convert("Java");
		System.out.println(converted2);    // "J"
	}
	
	
	
	
	
	static class Person {
	    String firstName;
	    String lastName;

	    Person() {}

	    Person(String firstName, String lastName) {
	        this.firstName = firstName;
	        this.lastName = lastName;
	    }
	    
	    public String toString() {
	    	return MoreObjects.toStringHelper(this).add("firstName", firstName).add("lastName", lastName).toString();
	    }
	}
	
	interface PersonFactory<P extends Person> {
	    P create(String firstName, String lastName);
	}
	
	// the :: keyword works for constructors
	@Test
	public void test03() {
		PersonFactory<Person> personFactory = (x, y) -> {
			return new Person(x, y);
		};
		Person person = personFactory.create("Peter", "Parker");
		System.out.println(person);
		
		// create a reference to the Person constructor via Person::new. 
		// The Java compiler automatically chooses the right constructor by matching the signature of PersonFactory.create
		personFactory = Person::new;
		person = personFactory.create("Peter", "Parker");
		System.out.println(person);
		
		
		personFactory = (x, y) -> {
			return new Person(x.toUpperCase(), y.toLowerCase());
		};
		person = personFactory.create("Peter", "Parker");
		System.out.println(person);
	}
	
	
	
}
