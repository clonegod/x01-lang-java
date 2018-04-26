package clonegod.mybatis.dao;

import clonegod.mybatis.v2.annotation.CGSelect;

public interface AuthorMapper {

	@CGSelect("SELECT * FROM author WHERE id = %d")
	Author selectByPrimaryKey(int id);
	
}
