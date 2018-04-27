package demo.guava.basic.precondition;

import com.google.common.base.Preconditions;

/**
 * Preconditions - 检查方法或构造函数被调用时是否传入了合法的参数
 *
 */
public class PreconditionsTester {

	public static void main(String[] args) {
		PreconditionsTester tester = new PreconditionsTester();
		
		try {
			tester.sqrt(-1.0);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		try {
			tester.sum(null, 2);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		try {
			tester.getValue(9);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	public double sqrt(double input) throws IllegalArgumentException {
		Preconditions.checkArgument(input > 0.0, "Illegal Argument passed: Negative value %s.", input);
		return Math.sqrt(input);
	}

	public int sum(Integer a, Integer b) {
		a = Preconditions.checkNotNull(a, "Illegal Argument passed: First parameter is Null.");
		b = Preconditions.checkNotNull(b, "Illegal Argument passed: Second parameter is Null.");
		return a + b;
	}

	public int getValue(int input) {
		int[] data = { 1, 2, 3, 4, 5 };
		Preconditions.checkElementIndex(input, data.length, "Illegal Argument passed: Invalid index.");
		return 0;
	}
}
