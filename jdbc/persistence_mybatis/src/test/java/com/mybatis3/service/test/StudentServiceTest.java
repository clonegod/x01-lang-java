package com.mybatis3.service.test;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mybatis3.domain.Address;
import com.mybatis3.domain.PhoneNumber;
import com.mybatis3.domain.Student;
import com.mybatis3.service.StudentService;

public class StudentServiceTest {
	
	StudentService studentService;
	
	@Before
	public void setUp() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:application*.xml");
		studentService = (StudentService) ctx.getBean("studentService");
	}
	
	@Test
	public void testTnsertStudent() {
		Student student = new Student();
		
		student.setName("test01");
		student.setEmail("test01@mail.com");
		student.setDob(new Date());
		Address addr = new Address();
		addr.setAddrId(1);
		
		PhoneNumber phoneNumber = new PhoneNumber("86", "010", "87530933");
		
		student.setPhoneNumber(phoneNumber);
		student.setAddress(addr);
		
		studentService.insertStudent(student);
	}
	
	@Test
	public void testDeleteStudent() {
		studentService.deleteStudent(1);
	}
	
	@Test
	public void testUpdateStudent() {
		Student student = new Student();
		student.setStudId(5);
		student.setName("5i");
		student.setDob(new Date());
		student.setPhoneNumber(new PhoneNumber("86", "023", "123999"));
		student.setEmail("5i@mail.com");
		studentService.updateStudent(student);
	}
	
	@Test
	public void testFindAllStudents() {
		List<Student> students = studentService.findAllStudents();
		System.out.println(students);
	}

	
}
