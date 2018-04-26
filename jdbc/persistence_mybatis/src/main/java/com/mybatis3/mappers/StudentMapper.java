package com.mybatis3.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mybatis3.domain.Student;

public interface StudentMapper {
	
	@Insert({ 
		"INSERT INTO STUDENTS(NAME,EMAIL,ADDR_ID, PHONE)", 
		"VALUES",
		"(#{name},#{email},#{address.addrId},#{phoneNumber})" 
	})
	@Options(useGeneratedKeys = true, keyProperty = "studId")
	int insertStudent(Student student);

	
	@Delete("DELETE FROM STUDENTS WHERE STUD_ID=#{studId}")
	int deleteStudent(int studId);

	
	@Update("UPDATE STUDENTS SET NAME=#{name}, EMAIL=#{email},DOB=#{dob}, PHONE=#{phoneNumber} WHERE STUD_ID=#{studId}")
	int updateStudent(Student student);

	
	@Select("SELECT * FROM STUDENTS")
	@Results({ 
		@Result(id = true, column = "stud_id", property = "studId"), 
		@Result(column = "name", property = "name"),
		@Result(column = "email", property = "email"), 
		@Result(column = "dob", property = "dob"), 
		@Result(column = "phone", property = "phoneNumber"), 
		@Result(column = "addr_id", property = "address.addrId") 
	})
	List<Student> findAllStudents();
	
}