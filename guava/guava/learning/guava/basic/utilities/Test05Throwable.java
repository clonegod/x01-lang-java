package guava.basic.utilities;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.Test;

import com.google.common.base.Throwables;

/**
 * Guava's Throwables utility can frequently simplify dealing with exceptions.
 *	
 *	Throwable
 *		Error 				- indicates serious problems that a reasonable application should not try to catch. 
 *		RuntimeException 	- Unchecked exceptions
 *		Exception 			- indicates conditions that a reasonable application might want to catch. 受检查异常
 *
 * In principle, unchecked exceptions indicate bugs, and checked exceptions indicate problems outside your control.
 * 	unchecked exceptions - NullPointerException，ArithmeticException，IllegalArgumentException...
 * 	checked exceptions - SQLException，IOException...
 * 
 * 异常处理
 * 	从异常中恢复
 * 		catch异常，处理异常，不再抛出
 * 	不处理异常，抛给调用者处理
 * 		不catch异常，直接往外抛
 * 		catch异常，有选择性的往外抛
 * 		catch异常，记录失败次数，记录错误日志，再往外抛
 * 		catch异常，包装为其它类型的异常，再往外抛
 */
public class Test05Throwable {
	
	class MyService {
		public void service1() {
			try {
				service2();
			} catch(Exception e) {
				throw new RuntimeException("服务调用异常", e);
			}
		}
		
		private void service2() {
			int number = 0;
			int avg = 100 / number;
		}
	}
	
	@Test
	public void test() {
		String input = "abc!";
		try {
			Integer.parseInt(input);
		} catch (NumberFormatException e) {
			e.printStackTrace();  // 控制台输出，不利于排查
			throw new InvalidInputException("xxx", e);
		}
	}
	
	@Test
	public void test1() {
		try {
			new MyService().service1();
		} catch (Exception e) {
			e.printStackTrace();  // 控制台输出，不利于排查
		}
	}
	
	@Test
	public void test2() {
		try {
			new MyService().service1();
		} catch (Exception e) {
			System.err.println("Log to database: " + e.getMessage()); // 异常信息太少，不利于跟踪错误
			System.err.println("Log to database: " + e.getCause().getMessage());
		}
	}
	
	@Test
	public void test3() throws Throwable {
		
		try {
			new MyService().service1();
		} catch (Exception e) {
			StringWriter sw = new StringWriter(); // 记录完整异常堆栈信息，麻烦
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.flush();
			System.err.println("Log to database: " + sw.toString()); 
		}
	}
	
	@Test
	public void test4() {
		try {
			new MyService().service1();
		} catch (Exception e) {
			String error = Throwables.getStackTraceAsString(e); // 输出异常堆栈信息
			System.err.println("Log to database: " + error);
		}
	}
	
	
	class InvalidInputException extends RuntimeException {
		public InvalidInputException() {
			super();
			// TODO Auto-generated constructor stub
		}

		public InvalidInputException(String message, Throwable cause, boolean enableSuppression,
				boolean writableStackTrace) {
			super(message, cause, enableSuppression, writableStackTrace);
			// TODO Auto-generated constructor stub
		}

		public InvalidInputException(String message, Throwable cause) {
			super(message, cause);
			// TODO Auto-generated constructor stub
		}

		public InvalidInputException(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}

		public InvalidInputException(Throwable cause) {
			super(cause);
			// TODO Auto-generated constructor stub
		}
		
	}

}
