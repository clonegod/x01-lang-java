package java8.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Test02LambdaExpressions {
	
	public static void main(String[] args) {
		List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
		
		// 1 anonymous comparators
		Collections.sort(names, new Comparator<String>() {
		    @Override
		    public int compare(String a, String b) {
		        return a.compareTo(b);
		    }
		});
		System.out.println(names);
		
		// 2 lambda expressions
		Collections.sort(names, (String a, String b) -> {
		    return b.compareTo(a);
		});
		System.out.println(names);
		
		
		// 3 For one line method bodies you can skip both the braces {} and the return keyword. 
		Collections.sort(names, (String a, String b) -> a.compareTo(b));
		System.out.println(names);
		
		
		// 4 The java compiler is aware of the parameter types so you can skip them as well.
		Collections.sort(names, (a, b) -> b.compareTo(a));
		System.out.println(names);
		
		
	}
	
	
	
}
