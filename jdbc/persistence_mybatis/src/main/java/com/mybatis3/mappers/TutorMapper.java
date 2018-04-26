package com.mybatis3.mappers;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.mybatis3.domain.Tutor;

public interface TutorMapper {
	
	@SelectProvider(type=TutorMapperProvider.class, method="findTutorByIdSql")  
	Tutor findTutorById(int tutorId);  
	
	@SelectProvider(type = TutorMapperProvider.class, method = "findTutorByNameAndEmailSql")  
	Tutor findTutorByNameAndEmail(String name, String email);  

	
	@SelectProvider(type = TutorMapperProvider.class, method = "selectTutorById")
	@ResultMap("com.mybatis3.mappers.TutorMapper.TutorResult")
	Tutor selectTutorById(int tutorId);
	
	
	@InsertProvider(type = TutorMapperProvider.class, method = "insertTutor")  
	@Options(useGeneratedKeys = true, keyProperty = "tutorId")  
	int insertTutor(Tutor tutor);


	
	@UpdateProvider(type = TutorMapperProvider.class, method = "updateTutor")  
	int updateTutor(Tutor tutor);
	 
	 
	@DeleteProvider(type = TutorMapperProvider.class, method = "deleteTutor")  
	int deleteTutor(int tutorId);
	 
}
