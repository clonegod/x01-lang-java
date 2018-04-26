package com.mybatis3.services;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mybatis3.domain.Tutor;

public class TutorServiceTest {
	
	private static TutorService tutorService;

	@BeforeClass
	public static void setup() {
		tutorService = new TutorService();
	}

	@AfterClass
	public static void teardown() {
		tutorService = null;
	}
	
	@Test
	public void testFindTutorById() {
		Tutor tutor = tutorService.findTutorById(2);
		System.out.println(tutor);
	}

}
