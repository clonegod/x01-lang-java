package com.mybatis.sample2.dao;

import com.mybatis.sample2.model.Blog;

public interface BlogMapper {
	int insertBlog(Blog blog);
	
	Blog selectBlogDetails(int id);
}
