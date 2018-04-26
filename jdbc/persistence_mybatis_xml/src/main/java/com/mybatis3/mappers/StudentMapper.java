package com.mybatis3.mappers;

import java.util.List;

import com.mybatis3.domain.Student;

/**
 * 创建了一个映射器Mapper接口-StudentMapper。
 * 其定义的方法签名和在StudentMapper.xml中定义的完全一样（即映射器Mapper接口中的方法名跟StudentMapper.xml中的id的值相同）。
 * 注意StudentMapper.xml中namespace的值被设置成com.mybatis3.mappers.StudentMapper，
 * 是StudentMapper接口的完全限定名。这使我们可以使用接口来调用映射的SQL语句。
 *
 */
public interface StudentMapper {
	
	List<Student> findAllStudents();

	Student findStudentById(Integer id);

	void insertStudent(Student student);
	
	
	void updateStudent(Student student);
	void updateStudentBySet(Student student);  
	
	void deleteStudent(int id);
	
	Student selectStudentWithAddress(int studId);

	
}
