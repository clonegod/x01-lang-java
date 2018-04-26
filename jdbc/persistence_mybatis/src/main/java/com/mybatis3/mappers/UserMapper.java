package com.mybatis3.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.mybatis3.domain.User;

public interface UserMapper {

	@Select("SELECT * FROM users WHERE user_name = #{username}")
	@Results(
		{
			@Result(column="user_name", property="username") // 字段名称与属性名称不相同，需单独说明映射关系
		}
	)
	User getUser(String username);
	
	
	/**
	 * 
	 * @param user
	 * @return 数据库id
	 */
	@Insert("INSERT INTO users (user_name, password) VALUES (#{username}, #{password})")
	@Options(useGeneratedKeys=true, keyProperty="id") // 回写插入记录的数据库ID值到参数（user对象）中
	int insertUser(User user);
	
	
	
	
}