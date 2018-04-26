package com.mybatis3.services;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mybatis3.domain.Tutor;
import com.mybatis3.mappers.TutorMapper;
import com.mybatis3.util.MyBatisSqlSessionFactory;

public class TutorService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public Tutor findTutorById(int tutorId) {
		logger.debug("Select Tutor By ID :{}", tutorId);
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			TutorMapper tutorMapper = sqlSession.getMapper(TutorMapper.class);  
			return tutorMapper.findTutorById(tutorId);
		} finally {
			sqlSession.close();
		}
	}
	
}
