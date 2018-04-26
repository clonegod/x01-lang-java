package com.clonegod.unittest.junit.parameter;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Use Junit4 ParameterizedTest 
 *  
 *  you can not mix parametrised with non-parametrised methods in one class
 *	缺点：
 *	1. 不能在一个测试类中同时编写带参数的case与普通case
 *	2. 参数注入不够灵活
 *
 *	优化方案：使用junitParams
 */
@RunWith(Parameterized.class)
public class ParameterizedByConstructorTest {
	
	
	// 为参数化测试case案例设置name
	@Parameters(name = "{index}: fib({0})={1}")
	//@Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {     
                 { 0, 0 }, { 1, 1 }, { 2, 1 }, { 3, 2 }, { 4, 3 }, { 5, 5 }, { 6, 8 }  
           });
    }

    private int fInput;

    private int fExpected;
    
    /**
     * 通过构造函数注入测试参数
     *  
     * @param input
     * @param expected
     */
    public ParameterizedByConstructorTest(int input, int expected) {
        fInput= input;
        fExpected= expected;
    }

    @Test
    public void test() {
        assertEquals(fExpected, Fibonacci.compute(fInput));
    }
    
    static class Fibonacci {
    	public static int compute(int n) {
    		int result = 0;
    		
    		if (n <= 1) { 
    			result = n; 
    		} else { 
    			result = compute(n - 1) + compute(n - 2); 
    		}
    		
    		return result;
    	}
    }
}
