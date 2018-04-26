package com.mybatis3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybatis3.domain.Student;
import com.mybatis3.mappers.StudentMapper;

@Service
@Transactional
public class StudentService {
	
	@Autowired
	private StudentMapper studentMapper;
	
	public int insertStudent(Student student) {
		return studentMapper.insertStudent(student);
	}
	
	public int deleteStudent(int studId) {
		return studentMapper.deleteStudent(studId);
	}
	
	
	public int updateStudent(Student student) {
		return studentMapper.updateStudent(student);
	}
	
	
	public List<Student> findAllStudents() {
		return studentMapper.findAllStudents();
	}
	
}
