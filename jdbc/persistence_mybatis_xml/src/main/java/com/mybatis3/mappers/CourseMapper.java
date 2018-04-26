package com.mybatis3.mappers;

import java.util.List;
import java.util.Map;

import com.mybatis3.domain.Course;

public interface CourseMapper {
	List<Course> searchCourses(Map<String, Object> map);
}
