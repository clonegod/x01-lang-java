package com.clonegod.jpa.repository;

//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.clonegod.jpa.entity.User;

//public interface UserRepository extends CrudRepository<User, Integer> {
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

	/**
	 * 可以在用户自定义接口中，再新增其它的数据操作接口。
	 * 但需要满足JPA接口定义规范：
	 * 1. 对查询方法而言，必须以 get | find | read 为前缀； 
	 * 2. 若包含条件查询，则条件属性用条件关键字连接，条件属性必须以首字母大写以示区分。
	 * 3. 更具体的规范，参考spring-jpa官方文档。
	 */
	
	/** 
	 * @param userName 基于用户名模糊查询
	 * @return
	 */
	public Iterable<User> findByNameLike(String userName);
	
	
}
