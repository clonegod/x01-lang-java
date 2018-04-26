package clonegod.framework.test.dal;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import clonegod.framework.dal.dao.BlogPostsMapper;
import clonegod.framework.dal.dao.Post;
import clonegod.framework.test.DalBaseTest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PagenationQueryTest extends DalBaseTest {
	
	@Autowired
	private BlogPostsMapper mapper;
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * sqlSessionTemplate 来实现分页功能
	 * 
	 */
	@Test
	public void testPageQuery() throws Exception {
		SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession();
		List<Object> lists = sqlSession.selectList("selectPostsForBlog", 1, new RowBounds(3, 2));
		lists.forEach(x -> {System.out.println(x);});
	}
	
	/**
	 * pageHelper 插件来实现分页功能
	 */
    @Test
    public void pagination分页插件() {
    	int pageSize = 1;
    	pageQuery(1, pageSize);
    	pageQuery(2, pageSize);
    	pageQuery(3, pageSize);
    	
    	System.out.println("================================");
    	pageSize = 2;
    	pageQuery(1, pageSize);
    	pageQuery(2, pageSize);
    }
    
    private void pageQuery(int startPageNo, int pageSize) {
    	PageInfo<Object> page = PageHelper.startPage(startPageNo, pageSize).doSelectPageInfo(new ISelect() {
            public void doSelect() {
                List<Post> posts = mapper.selectAll();
                posts.forEach(p -> log.info(p.toString()));
            }
        });
        log.info(" {}", page);
    }
}
