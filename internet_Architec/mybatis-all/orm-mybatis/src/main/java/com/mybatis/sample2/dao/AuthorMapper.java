package com.mybatis.sample2.dao;

import java.util.List;

import com.mybatis.sample2.model.Author;

public interface AuthorMapper {
	
	int insertAuthor(Author author);
	
	List<Author> selectAuthorByUsernameOrEmail(String username, String email);
}
