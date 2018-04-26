package com.mybatis3.services;

import java.util.*;
import org.junit.*;
import com.mybatis3.domain.Student;

public class StudentServiceTest {
	
	private static StudentService studentService;

	@BeforeClass
	public static void setup() {
		studentService = new StudentService();
	}

	@AfterClass
	public static void teardown() {
		studentService = null;
	}

	@Test
	public void testFindAllStudents() {
		List<Student> students = studentService.findAllStudents();
		Assert.assertNotNull(students);
		for (Student student : students) {
			System.out.println(student);
		}
	}

	@Test
	public void testFindStudentById() {
		Student student = studentService.findStudentById(1);
		Assert.assertNotNull(student);
		System.out.println(student);
	}

	@Test
	public void testCreateStudent() {
		Student student = new Student();
		student.setName("student_x");
		student.setEmail("student_x_gmail.com");
		student.setDob(new Date());
		studentService.createStudent(student);
		Student newStudent = studentService.findStudentById(student.getStudId());
		Assert.assertNotNull(newStudent);
	}
	
	@Test
	public void testUpdateStudent() {
		Student student = new Student();
		student.setStudId(1);
		student.setName("student_1");
		student.setEmail("student1@mail.com");
		studentService.updateStudent(student);
	}
	
	@Test
	public void testUpdateStudentBySet() {
		Student student = new Student();
		student.setStudId(1);
		student.setDob(new Date());
		studentService.updateStudentBySet(student);
	}
	
	@Test
	public void deleteStudent() {
		studentService.deleteStudent(1);
	}
	
	@Test
	public void testSelectStudentWithAddress() {
		Student stu = studentService.selectStudentWithAddress(1);
		System.out.println(stu.getName());
		System.out.println(stu.getAddress().getCity());
	}
}
