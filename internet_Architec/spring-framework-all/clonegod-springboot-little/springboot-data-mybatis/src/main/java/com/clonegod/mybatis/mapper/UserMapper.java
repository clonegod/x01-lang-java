package com.clonegod.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.clonegod.mybatis.domain.User;

@Mapper
public interface UserMapper {
	
	/**
	 * 根据name进行模糊查询
	 * 
	 * @param name
	 * @return
	 */
	@Select("SELECT id, name, age, create_Time as createTime FROM t_user WHERE name LIKE #{name} ORDER BY id ASC")
	List<User> getAllUsersByNameLike(String name);
	
	/**
	 * 根据id查询user
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT id, name, age, create_Time as createTime FROM t_user WHERE id = #{id}")
	User getUserById(int id);
	
	/**
	 * 保存用户
	 * 
	 * @param user
	 * @return	影响的行数
	 */
	@Insert("INSERT INTO t_user(name, age, create_time) VALUES (#{name}, #{age}, #{createTime})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id") // mybatis会自动将返回的id设置到user对象中
	int saveUser(User user);
	
	/**
	 * SQL语句配置在UserMapper.xml中
	 * 
	 * @param id
	 * @return
	 */
	public String getUserNameById(int id);
	
}
