package com.asynclife.test.spel;

import java.util.GregorianCalendar;
import java.util.Random;

import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.expression.spel.support.StandardTypeLocator;

import com.asynclife.spel.Inventor;

public class SpelTestDriver {

	@Test
	public void test() {
		// Create and set a calendar 
		GregorianCalendar c = new GregorianCalendar();
		c.set(1856, 7, 9);

		//  The constructor arguments are name, birthday, and nationality.
		Inventor tesla = new Inventor("Nikola Tesla", c.getTime(), "Serbian");

		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("name");
		EvaluationContext context = new StandardEvaluationContext(tesla);

		String name = (String) exp.getValue(context);
		System.out.println(name);
	}
	
	
	/**
	 * 测试多线程环境下，Context保持变量的安全性
	 */
	@Test
	public void testContextThreadSafe() {
		
		// 多线程共享1个context，会造成数据覆盖问题！
		// final StandardEvaluationContext context = new StandardEvaluationContext();
		
		for(int i=0; i<10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					// each thread has it's own context, thus, thread is safe in concurrent
					StandardEvaluationContext context = new StandardEvaluationContext();
					context.setVariable("T_name", Thread.currentThread().getName());
					try {
						Thread.sleep(new Random().nextInt(100));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Object obj = context.lookupVariable("T_name");
					System.out.println(obj);
				}
			}).start();
		}
		
		try {
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 引入自定义类型的package-prefix
	 */
	@Test
	public void testRegisterImport() {
		StandardTypeLocator typeLocator = new StandardTypeLocator();
		typeLocator.registerImport("com.asynclife.spel");

		StandardEvaluationContext context = new StandardEvaluationContext();
		context.setTypeLocator(typeLocator);
		
		ExpressionParser parser = new SpelExpressionParser();
		// Expression exp = parser.parseExpression("T(CalcUtil).print('a')");
		Expression exp = parser.parseExpression("T(com.asynclife.spel.CalcUtil).print('a')");
		
		String ret = exp.getValue(context, String.class);
		
		System.out.println(ret);
		
	}
	
}
