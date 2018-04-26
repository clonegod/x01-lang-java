package com.mybatis3.service.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mybatis3.domain.Tutor;
import com.mybatis3.service.TutorService;

public class TutorServiceTest {

	TutorService tutorService;
	
	@Before
	public void setUp() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:application*.xml");
		tutorService = (TutorService) ctx.getBean("tutorService");
	}
	
	@Test
	public void testFindTutorByIdUseSqlProvider() {
		Tutor tutor = tutorService.findTutorById(1);
		System.out.println(tutor);
	}
	
	@Test
	public void testFindTutorByNameAndEmailUseSqlProvider() {
		Tutor tutor = tutorService.findTutorByNameAndEmail("John", "john@gmail.com");
		System.out.println(tutor);
	}
	
	@Test
	public void testSelectTutorByIdUseSqlProvider() {
		Tutor tutor = tutorService.selectTutorById(1);
		System.out.println(tutor);
	}
	
	@Test
	public void testInsertTutorUseSqlProvider() {
		Tutor tutor = new Tutor();
		tutor.setName("tutor01");
		tutorService.insertTutor(tutor);
	}
	
	@Test
	public void testUpdateTutorUseSqlProvider() {
		Tutor tutor = new Tutor();
		tutor.setTutorId(3);
		tutor.setName("tutor02");
		tutor.setEmail("tutor02@mail.com");
		tutorService.updateTutor(tutor);
	}
	
	@Test
	public void testDeleteTutorUseSqlProvider() {
		tutorService.deleteTutor(3);
	}
}
