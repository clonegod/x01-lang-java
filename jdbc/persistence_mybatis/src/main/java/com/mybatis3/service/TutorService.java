package com.mybatis3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybatis3.domain.Tutor;
import com.mybatis3.mappers.TutorMapper;

@Service
@Transactional
public class TutorService {
	
	@Autowired
	private TutorMapper tutorMapper;
	
	public Tutor findTutorById(int tutorId) {
		return tutorMapper.findTutorById(tutorId);
	}
	
	public Tutor findTutorByNameAndEmail(String name, String email) {
		return tutorMapper.findTutorByNameAndEmail(name, email);
	}
	
	public Tutor selectTutorById(int tutorId) {
		return tutorMapper.selectTutorById(tutorId);
	}
	
	public int insertTutor(Tutor tutor) {
		return tutorMapper.insertTutor(tutor);
	}
	
	public int updateTutor(Tutor tutor) {
		return tutorMapper.updateTutor(tutor);
	}
	
	public int deleteTutor(int tutorId) {
		return tutorMapper.deleteTutor(tutorId);
	}
}
