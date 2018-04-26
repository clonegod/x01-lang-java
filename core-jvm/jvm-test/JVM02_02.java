package com.aysnclife.dataguru.jvm;

import org.junit.Test;

public class JVM02_02 {
	
	// 总递归/循环次数
	private final static int TOTAL_TIMES = 100000; //10万

	/**  
	 * 程序功能：通过递归调用进行自然数的相加操作。
	 * 
	 * 分析：由于java线程栈的空间是有限的，因此支持的连续入栈动作也是有限的。如果递归调用层次太深，将会引发栈溢出的异常。
	 * 
	 * 通过设置JVM参数来配置线程栈的大小，下面进行一组测试：
	 * 	1. -Xss128k 当调用次数达到“第669次调用”左右时，栈溢出-java.lang.StackOverflowError
	 * 	2. -Xss512k 当调用次数达到“第3957次调用”左右时，栈溢出-java.lang.StackOverflowError
	 * 	3. -Xss1M 	当调用次数达到“第8322次调用”左右时，栈溢出-java.lang.StackOverflowError
	 * 	4. -Xss2M 	当调用次数达到“第17079次调用”左右时，栈溢出-java.lang.StackOverflowError
	 * 以上测试可说明一个问题：栈的大小与可支持递归调用次数是成正比例关系的。
	 * 因此，可得出一个StackOverflowError的解决方案：增加栈空间的分配。
	 * 
	 * 线程栈空间分配越大越好吗？
	 * 线程栈的大小是个双刃剑，如果设置过小，可能会出现栈溢出，特别是在该线程内有递归时出现溢出的可能性更大；
	 * 如果该值设置过大，就有影响到创建栈的数量，如果是多线程的应用，就会出现内存溢出的错误。
	 */
	@Test
	public void test_recursive() {
		try {
			long sum = recursion(1, TOTAL_TIMES);
			System.out.println(sum);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private long recursion(int i, int totalTimes) {
		System.out.println(String.format("第%s次调用", i));
		if(--totalTimes > 0) {
			return i + recursion(++i, totalTimes);
		}
		return 0;
	}
	
	/**
	 * 程序功能：通过循环来实现自然数的相加操作。
	 * 
	 * 分析：
	 * 对于每一个线程,都有一个java栈 ,当有一个方法被调用的时候,会产生一些跟这个方法相关的信息,如方法名，参数，中间变量等等,这些叫做栈帧 。
	 * 当一个方法执行完毕，这个栈帧才会从栈顶pop掉。
	 * 递归执行过程中会一直向栈里push栈帧，而java栈是有一定的长度或深度的,当栈满了,无法再进行push的时候 就出现堆栈溢出异常了。
	 * 
	 * 解决办法：
	 * 用循环(for,while)来替代递归操作，这样就可以避免由于栈空间小而引发的堆栈溢出问题。
	 * 由于循环操作是在方法内部进行的，因此方法只入栈1次，程序执行效率也更高。
	 * 
	 */
	@Test
	public void test_nonRecursive() {
		int i = 0;
		long sum = 0;
		while(++i <= TOTAL_TIMES) {
			System.out.println(String.format("第%s次调用", i));
			sum += i;
		}
		System.out.println("sum="+sum);
	}
}
