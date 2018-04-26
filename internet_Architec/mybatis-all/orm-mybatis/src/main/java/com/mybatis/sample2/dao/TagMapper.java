package com.mybatis.sample2.dao;

import com.mybatis.sample2.model.Tag;

public interface TagMapper {
	int insertTag(Tag tag);
	
	Tag selectAnyTag();
}
