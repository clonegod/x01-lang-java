package java8.core;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Test05FunctionalInterfaces {
	
	/**
	 * functional interface must contain exactly one abstract method declaration
	 * 
	 * Since default methods are not abstract you're free to add default methods to your functional interface.
	 */
	
	/**
	 * We can use arbitrary interfaces as lambda expressions as long as the interface only contains one abstract method. 
	 * To ensure that your interface meet the requirements, you should add the @FunctionalInterface annotation. 
	 * The compiler is aware of this annotation and throws a compiler error as soon as you try to add a second abstract method declaration to the interface.
	 *
	 * @param <F>	type of input
	 * @param <T>	type of result
	 */
	@FunctionalInterface
	interface Converter<F, T> {
	    T convert(F from);
	    
	    default Integer convert2(String from) {
	    	return Integer.parseInt(from.concat("00"));
	    }
	}
	
	public void test1() {
		Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
		Integer converted = converter.convert("123");
		System.out.println(converted);    // 123
		
		Integer converted2 = converter.convert2("123");
		System.out.println(converted2);
		
	}
	
	
	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		
		filter(list, n -> true);
		
		filter(list, n -> n > 5);
		
		list.stream().filter(n -> n % 2 == 0).forEach(System.out::println);
	}

	public static void filter(List<Integer> list, Predicate<Integer> predicate) {
		for (Integer n : list) {
			if (predicate.test(n)) {
				System.out.print(n + " ");
			}
		}
		System.out.println();
	}
	
}
