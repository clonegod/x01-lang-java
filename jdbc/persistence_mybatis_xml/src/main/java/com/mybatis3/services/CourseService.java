package com.mybatis3.services;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.mybatis3.domain.Course;
import com.mybatis3.mappers.CourseMapper;
import com.mybatis3.util.MyBatisSqlSessionFactory;

public class CourseService {
	
	public List<Course> searchCourses(Map<String,Object> map) {
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
		List<Course> courses = mapper.searchCourses(map);
		return courses;
	}
}
