package clonegod.framework.dal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
	
	// annotation 和 xml 是互补的关系
	@Select("SELECT * FROM post WHERE 1 = 1")
	List<Post> selectAll();

	int insertBatch(List<Post> posts);
    
	BlogPostsResultMap selectBlogPosts(Integer blogId);
	
	BlogPostsResultMap selectBlogAllRelatedInfoById(Integer blogId);
	
	
}
