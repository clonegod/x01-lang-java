package com.clonegod.unittest.junit.category;

import org.junit.Test;
import org.junit.experimental.categories.Category;

public class A3Test {
	@Test
	@Category(SlowTests.class)
	public void testSlow() {
		System.out.println("slow");
	}

	@Test
	@Category(SlowerTests.class)
	public void testSlower() {
		System.out.println("slower");
	}

	@Test
	@Category(FastTests.class)
	public void testFast() {
		System.out.println("fast");
	}
}