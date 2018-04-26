package com.clonegod.web.controler;

import org.junit.Test;

import com.clonegod.web.SpringBootTestBase;

public class ErrorControllerTest extends SpringBootTestBase {
	@Test
	public void testErrorHandler() {
		String error = testRestTemplate.getForObject("/npe", String.class);
		System.out.println(error);
	}
	
	@Test
	public void testErrorPage() {
		String error = testRestTemplate.getForObject("/java", String.class);
		System.out.println(error);
	}
}
