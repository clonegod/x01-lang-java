package com.clonegod.unittest.junit;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ExceptionTest {
	
	/**
	 * 在@Test注解上声明期望发生的异常类型
	 * @Test 注解有一个可选参数“expected”，它接受任何Throwable子类。
	 */
	@Test(expected = IndexOutOfBoundsException.class) 
	public void empty() { 
	     new ArrayList<Object>().get(0); 
	}

	/**
	 * 在try...catch块中对异常进行检测/断言
	 */
	@Test
	public void testExceptionMessage() {
	    try {
	        new ArrayList<Object>().get(0);
	        fail("Expected an IndexOutOfBoundsException to be thrown");
	    } catch (IndexOutOfBoundsException anIndexOutOfBoundsException) {
	        assertThat(anIndexOutOfBoundsException.getMessage(), is("Index: 0, Size: 0"));
	    }
	}
	
	/**
	 * 使用ExpectedException rule.
	 * 	不仅可以检测期望的异常，还可以检测期望的异常消息：
	 */
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldTestExceptionMessage() throws IndexOutOfBoundsException {
	    List<Object> list = new ArrayList<Object>();
	 
	    thrown.expect(IndexOutOfBoundsException.class);
	    thrown.expectMessage("Index: 0, Size: 0");
	    list.get(0); // execution will never get past this line
	}
	
	 @Test
	  public void throwsNullPointerExceptionWithMessage() {
	    thrown.expect(NullPointerException.class);
	    thrown.expectMessage("happened?");
	    thrown.expectMessage(startsWith("What"));
	    throw new NullPointerException("What happened?");
	  }
}
