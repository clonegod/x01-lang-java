package com.mybatis.sample2.dao;

import com.mybatis.sample2.model.Post;

public interface PostMapper {
	int insertPost(Post post);
	
	Post selectPostById(int postId);
}
