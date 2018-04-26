package com.clonegod.unittest.mock;

import static org.mockito.Mockito.*;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class SimpleMockTest {
	@Test
	public void simpleTest() {
		// 创建mock对象，参数可以是类，也可以是接口.不能对final，Anonymous ，primitive类进行mock。
		List<String> list = mock(List.class);
		// 设置方法的预期返回值
		when(list.get(0)).thenReturn("helloworld");
		String result = list.get(0);
		// 验证方法调用(是否调用了get(0))
		verify(list).get(0);
		// junit测试
		Assert.assertEquals("helloworld", result);
	}
	
	@Test
	public void simpleTest2() {
		// 创建mock对象，参数可以是类，也可以是接口.不能对final，Anonymous ，primitive类进行mock。
		List<String> list = mock(List.class);
		// 设置方法的预期返回值
		doReturn("secondhello").when(list).get(1);
		String result = list.get(1);
		verify(list).get(1);
		Assert.assertEquals("secondhello", result);
	}
	
	@Test(expected=RuntimeException.class)
	public void testException() {
		List<String> list = mock(List.class);
		when(list.get(1)).thenThrow(new RuntimeException("test excpetion"));
		list.get(1);
	}
	
	// 没有返回值的void方法与其设定(支持迭代风格:第一次调用donothing,第二次dothrow抛出runtime异常)
	@Test(expected=IllegalArgumentException.class)
	public void testMethodReturnVoid() {
		List<String> list = mock(List.class);
		doNothing().doThrow(new IllegalArgumentException("void exception")).when(list).clear();  
		list.clear();  
		list.clear();  
		verify(list,times(2)).clear();
	}
	
	// Matchers类内加你有很多参数匹配器 anyInt、anyString、anyMap.....Mockito类继承于Matchers,Stubbing时使用内建参数匹配器
	// 如果使用参数匹配器，那么所有的参数都要使用参数匹配器，不管是stubbing还是verify的时候都一样。
	@Test  
	public void argumentMatcherTest(){  
	    List<String> list = mock(List.class);  
	    when(list.get(anyInt())).thenReturn("hello","world");  
	    String result = list.get(0)+list.get(1);  
	    verify(list,times(2)).get(anyInt());  
	    Assert.assertEquals("helloworld", result);  
	}
}