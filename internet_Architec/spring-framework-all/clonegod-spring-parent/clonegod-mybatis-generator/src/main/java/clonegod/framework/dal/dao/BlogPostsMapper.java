package clonegod.framework.dal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import clonegod.framework.dal.dao.resultmap.BlogPostsResultMap;

/**
 * 自定义的扩展Mapper，非mybatis generator生成！ 
 * 
 * 自定义多表联合查询
 * 
 */
@Mapper
public interface BlogPostsMapper {
    
	List<Post> selectPosts(@Param("subject")String subject, @Param("blogId")Integer blogId);

	int insertBatch(List<Post> posts);
    
	BlogPostsResultMap selectBlogPosts(Integer blogId);
	
	BlogPostsResultMap selectBlogAllRelatedInfoById(Integer blogId);
	
	
}
