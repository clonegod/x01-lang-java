package com.mybatis3.services;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mybatis3.domain.Student;
import com.mybatis3.mappers.StudentMapper;
import com.mybatis3.util.MyBatisSqlSessionFactory;

public class StudentService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public List<Student> findAllStudents() {
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
			return studentMapper.findAllStudents();
		} finally {
			// If sqlSession is not closed
			// then database Connection associated this sqlSession will not be
			// returned to pool
			// and application may run out of connections.
			sqlSession.close();
		}
	}

	public Student findStudentById(Integer studId) {
		logger.debug("Select Student By ID :{}", studId);
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			
			// 方法一：直接调用映射器配置文件xml
			return sqlSession.selectOne("com.mybatis3.mappers.StudentMapper.findStudentById", studId);  
			
			// 方法二：通过接口进行调用（使用映射器Mapper接口提供类型安全的方式进行调用）
			// StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
			// return studentMapper.findStudentById(studId);
		} finally {
			sqlSession.close();
		}
	}

	public void createStudent(Student student) {
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
			studentMapper.insertStudent(student);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}
	
	public void updateStudent(Student student) {
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
			studentMapper.updateStudent(student);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}
	
	public void updateStudentBySet(Student student) {
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
			studentMapper.updateStudentBySet(student);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}
	
	public void deleteStudent(int id) {
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
			studentMapper.deleteStudent(id);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}
	
	
	public Student selectStudentWithAddress(int studId) {
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);  
			return studentMapper.selectStudentWithAddress(studId);  
		} finally {
			sqlSession.close();
		}
	}
	
	
}
