package java8.core;

import org.junit.Test;


public class Test03LambdaScopes {
	
	@FunctionalInterface
	interface Converter<F, T> {
	    T convert(F from);
	    
	    default Integer convert2(String from) {
	    	return Integer.parseInt(from.concat("00"));
	    }
	}
	
	/** 1 Accessing local variables */
	@Test
	public void test1() {
		// num must be implicitly final for the code to compile.
		final int num = 1;
		// We can read final local variables from the outer scope of lambda expressions
		Converter<Integer, String> stringConverter =
		        (from) -> String.valueOf(from + num);

		
		System.out.println(stringConverter.convert(2));     // 3
	}
	
	
	
	
	
	
	/** 2 Accessing fields and static variables */
	static class Lambda4 {
	    static int outerStaticNum;
	    int outerNum;

	    String testScopes1(Integer input) {
	        Converter<Integer, String> stringConverter2 = (from) -> {
	            outerStaticNum = 72;
	            return String.valueOf(from + outerNum + outerStaticNum);
	        };
	        return stringConverter2.convert(input);
	    }
	    String testScopes2(Integer input) {
	    	Converter<Integer, String> stringConverter1 = (from) -> {
	    		outerNum = 23;
	    		return String.valueOf(from + outerNum + outerStaticNum);
	    	};
	    	return stringConverter1.convert(input);
	    }
	}
	
	@Test
	public void test2() {
		Lambda4 obj = new Lambda4();
		System.out.println(obj.testScopes1(1));
		System.out.println(obj.testScopes2(1));
	}
	
	
	
	
	
	
	
	
	
	/** 3 Accessing Default Interface Methods */
	interface Formula {
	    double calculate(int a);
	    
	    default double sqrt(int a) {
	        return Math.sqrt(a);
	    }
	}
	
	static class FormulaImpl implements Formula {
		@Override
		public double calculate(int a) {
			return sqrt(a / 2);
		}
	}
	
	@Test
	public void test3() {
		// accessed from formula instance
		Formula formula = new FormulaImpl(); 
		double result = formula.calculate(100);
		System.out.println(result);
		
		// accessed from formula instance including anonymous objects.
		result = new Formula() {
			@Override
			public double calculate(int n) {
				return n * 2;
			}
		}.sqrt(100);
		
		System.out.println(result);
		
	}
	
	
	
	
}
