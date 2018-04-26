package com.clonegod.unittest.junitparams;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class JUnitParamsTest {
	
	@Test
	public void testWithNoParmater() {
		assertTrue(true);
	}
	
	
	@Test
	@Parameters({ 
	        "17, false", 
	        "22, true" })
	public void personIsAdultDefineParamsInAnnotaion(int age, boolean valid) throws Exception {
	    assertThat(new Person(age).isAdult(), is(valid));
	}

	@Test
	@Parameters(method = "adultValues")
	public void personIsAdultWithMoreParams(int age, boolean valid) throws Exception {
	    assertEquals(valid, new Person(age).isAdult());
	}

	Object[] adultValues() {
	    return new Object[]{
	                 new Object[]{13, false},
	                 new Object[]{17, false},
	                 new Object[]{18, true},
	                 new Object[]{22, true}
	            };
	}
	
	/**
	 * 如果方法名比較短，可以使用parametersForXXX來为XXX测试案例绑定测试参数，这样就不需要在@Parameters中设置method属性了。
	 * @param age
	 * @param valid
	 * @throws Exception
	 */
	@Test
	@Parameters
	public void personIsAdult(int age, boolean valid) throws Exception {
	    assertEquals(valid, new Person(age).isAdult());
	}

	Object[] parametersForPersonIsAdult() {
	    return new Object[]{
	                 new Object[]{13, false},
	                 new Object[]{17, false},
	                 new Object[]{18, true},
	                 new Object[]{22, true}
	            };
	}
	
	/**
	 * 测试案例的方法入参是对象的情况
	 * @param person
	 * @param valid
	 * @throws Exception
	 */
	@Test
	@Parameters
	public void isAdult(Person person, boolean valid) throws Exception {
	    assertThat(person.isAdult(), is(valid));
	}

	Object[] parametersForIsAdult() {
	    return new Object[]{
	                 new Object[]{new Person(13), false},
	                 new Object[]{new Person(17), false},
	                 new Object[]{new Person(18), true},
	                 new Object[]{new Person(22), true}
	            };
	}
	
	/**
	 * 从外部读取测试数据:
	 * 测试数据可以是固定值，或从文件读取，数据库读取，这样就使得测试数据源更加灵活。
	 */
	@Test
	@Parameters(source = PersonProvider.class)
	public void personIsAdult(Person person, boolean valid) {
	    assertThat(person.isAdult(), is(valid));
	}
	
}

