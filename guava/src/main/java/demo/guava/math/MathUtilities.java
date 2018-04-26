package demo.guava.math;

import java.math.BigInteger;
import java.math.RoundingMode;

import org.junit.Test;

import com.google.common.math.BigIntegerMath;
import com.google.common.math.IntMath;

public class MathUtilities {

	@Test
	public void testIntMath() {
		new IntMathTester().testIntMath();
	}
	class IntMathTester {
		public void testIntMath() {
			try {
				System.out.println(IntMath.checkedAdd(Integer.MAX_VALUE, Integer.MAX_VALUE));
			} catch (ArithmeticException e) {
				System.err.println("Error: " + e.getMessage());
			}
			System.out.println(IntMath.divide(100, 5, RoundingMode.UNNECESSARY));
			try {
				// exception will be thrown as 100 is not completely divisible
				// by 3 thus rounding
				// is required, and RoundingMode is set as UNNESSARY
				System.out.println(IntMath.divide(100, 3, RoundingMode.UNNECESSARY));
			} catch (ArithmeticException e) {
				System.err.println("Error: " + e.getMessage());
			}
			System.out.println("Log2(2): " + IntMath.log2(2, RoundingMode.HALF_EVEN));
			System.out.println("Log10(10): " + IntMath.log10(10, RoundingMode.HALF_EVEN));
			System.out.println("sqrt(100): " + IntMath.sqrt(IntMath.pow(10, 2), RoundingMode.HALF_EVEN));
			System.out.println("gcd(100,50): " + IntMath.gcd(100, 50));
			System.out.println("modulus(100,50): " + IntMath.mod(100, 50));
			System.out.println("factorial(5): " + IntMath.factorial(5));
		}
	}

	
	@Test
	public void testBigIntegerMath() {
		new BigIntegerMathTester().testBigIntegerMath();
	}
	class BigIntegerMathTester {
		public void testBigIntegerMath() {
			System.out.println(BigIntegerMath.divide(BigInteger.TEN, new BigInteger("2"), RoundingMode.UNNECESSARY));
			try {
				// exception will be thrown as 10 is not completely divisible by 3,
				// thus rounding is required, and RoundingMode is set as UNNESSARY
				System.out
						.println(BigIntegerMath.divide(BigInteger.TEN, new BigInteger("3"), RoundingMode.UNNECESSARY));
			} catch (ArithmeticException e) {
				System.err.println("Error: " + e.getMessage());
			}
			System.out.println("Log2(2): " + BigIntegerMath.log2(new BigInteger("2"), RoundingMode.HALF_EVEN));
			System.out.println("Log10(10):" + BigIntegerMath.log10(BigInteger.TEN, RoundingMode.HALF_EVEN));
			System.out.println("sqrt(100):"
					+ BigIntegerMath.sqrt(BigInteger.TEN.multiply(BigInteger.TEN), RoundingMode.HALF_EVEN));
			System.out.println("factorial(5): " + BigIntegerMath.factorial(5));
		}
	}

}
