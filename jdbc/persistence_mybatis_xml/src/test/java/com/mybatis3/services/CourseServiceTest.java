package com.mybatis3.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mybatis3.domain.Course;

public class CourseServiceTest {
	
	private static CourseService courseService;

	@BeforeClass
	public static void setup() {
		courseService = new CourseService();
	}

	@AfterClass
	public static void teardown() {
		courseService = null;
	}
	
	@Test
	public void testQueryCondition() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tutorId", 1);
		map.put("courseName", "%java%");
		map.put("startDate", new SimpleDateFormat("yyyyMMdd").parse("19900101"));
		List<Course>  courses = courseService.searchCourses(map);
		for (Course course : courses) {
			System.out.println(course);
		}
	}
	
}
