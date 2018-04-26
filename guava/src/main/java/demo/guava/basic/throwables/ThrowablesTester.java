package demo.guava.basic.throwables;

import java.io.IOException;

import com.google.common.base.Throwables;

/**
 * 异常处理辅助类
 * 	1. 抛出异常
 * 		Throwables.propagate(e);
 * 		Throwables.propagateIfInstanceOf(e, InvalidInputException.class);
 * 		Throwables.propagateIfPossible(e, InvalidInputException.class, IOException.class);
 * 	2. 捕获异常后获取异常内容
 * 		Throwables.getRootCause(e)
 * 		Throwables.getStackTraceAsString(e)
 *
 */
public class ThrowablesTester {
	public static void main(String[] args) {
		ThrowablesTester tester = new ThrowablesTester();
		
		try {
			tester.showcaseThrowables();
		} catch (InvalidInputException e) {
			// get the root cause
			System.out.println(Throwables.getRootCause(e));
		} catch (Exception e) {
			// get the stack trace in string format
			System.out.println(Throwables.getStackTraceAsString(e));
		}
		
		try {
			tester.showcaseThrowables1();
		} catch (Exception e) {
			System.out.println(Throwables.getStackTraceAsString(e));
		}
		
		try {
			tester.showcaseThrowables2();
		} catch (IOException e) {
			System.out.println(Throwables.getRootCause(e));
		} catch (Exception e) {
			System.out.println(Throwables.getStackTraceAsString(e));
		}
	}

	public void showcaseThrowables() throws InvalidInputException {
		try {
			sqrt(-3.0);
		} catch (Throwable e) {
			// check the type of exception and throw it
			Throwables.throwIfInstanceOf(e, InvalidInputException.class);
			Throwables.propagate(e);
		}
	}

	public void showcaseThrowables1() {
		try {
			int[] data = { 1, 2, 3 };
			getValue(data, 4);
		} catch (Throwable e) {
			Throwables.throwIfInstanceOf(e, IndexOutOfBoundsException.class);
			Throwables.propagate(e);
		}
	}
	
	public void showcaseThrowables2() throws IOException, InvalidInputException {
		try {
			dummyIO();
		} catch (Throwable e) {
			Throwables.propagateIfPossible(e, InvalidInputException.class, IOException.class);
			Throwables.propagate(e);
		}
	}

	public double sqrt(double input) throws InvalidInputException {
		if (input < 0)
			throw new InvalidInputException();
		return Math.sqrt(input);
	}

	public double getValue(int[] list, int index) throws IndexOutOfBoundsException {
		return list[index];
	}

	public void dummyIO() throws IOException {
		throw new IOException();
	}
}

@SuppressWarnings("serial")
class InvalidInputException extends Exception {}