package com.mybatis3.mappers;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.mybatis3.domain.Tutor;

public class TutorMapperProvider {

	public String findTutorByIdSql(int tutorId) {
		// return "SELECT TUTOR_ID AS tutorId, NAME, EMAIL FROM TUTORS WHERE
		// TUTOR_ID=" + tutorId;

		return new SQL() {
			{
				SELECT("tutor_id as tutorId, name, email");
				FROM("tutors");
				WHERE("tutor_id=" + tutorId);
			}
		}.toString();
	}

	public String findTutorByNameAndEmailSql(Map<String, Object> map) {
		// String name = (String) map.get("param1");
		// String email = (String) map.get("param2");
		
		// you can also get those values using 0,1 keys
		// String name = (String) map.get("0");
		// String email = (String) map.get("1");
		return new SQL() {
			{
				SELECT("tutor_id as tutorId, name, email");
				FROM("tutors");
				WHERE("name=#{param1} AND email=#{param2}");
			}
		}.toString();
	}
	
	
	public String selectTutorById() {
		return new SQL()
		{
			{
				SELECT("t.tutor_id, t.name as tutor_name, email");
				SELECT("a.addr_id, street, city, state, zip, country");
				SELECT("course_id, c.name, description, start_date, end_date");
				FROM("TUTORS t");
				LEFT_OUTER_JOIN("addresses a on t.addr_id=a.addr_id");
				LEFT_OUTER_JOIN("courses c on t.tutor_id=c.tutor_id");
				WHERE("t.TUTOR_ID = #{id}");
			}
		} .toString();
	}
	
	
	public String insertTutor(final Tutor tutor) {  
        return new SQL()  
        {  
            {  
                INSERT_INTO("TUTORS");  
                
                if (tutor.getName() != null) {  
                    VALUES("NAME", "#{name}");  
                }
                
                if (tutor.getEmail() != null) {  
                    VALUES("EMAIL", "#{email}");  
                } else {
                	VALUES("EMAIL", "'UNKONWN'");  
                }
            }  
        } .toString();  
    }  

	
	public String updateTutor(final Tutor tutor) {  
        return new SQL()  
        {  
            {  
                UPDATE("TUTORS");  
                if (tutor.getName() != null)  
                {  
                    SET("NAME = #{name}");  
                }  
                if (tutor.getEmail() != null)  
                {  
                    SET("EMAIL = #{email}");  
                }  
                WHERE("TUTOR_ID = #{tutorId}");  
            }  
        } .toString();  
    } 
	
	 public String deleteTutor(int tutorId)  
	    {  
	        return new SQL()  
	        {  
	            {  
	                DELETE_FROM("TUTORS");  
	                WHERE("TUTOR_ID = #{tutorId}");  
	            }  
	        } .toString();  
	    }  
	}
