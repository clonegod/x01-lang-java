package demo.mybatis.typehandler;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;

import clonegod.framework.dal.dao.Author;
import clonegod.framework.dal.dao.AuthorEnumMapper;
import clonegod.framework.dal.enums.CurrencyEnum;
import clonegod.mybatis.utils.MybatisUtil;

@FixMethodOrder
public class TestTypeHandler {
	/**
	 * 自定义TypeHandler处理枚举类型的插入
	 */
    @Test
    public void test01_InsertEnum() throws Exception {
    	SqlSession sqlSession = MybatisUtil.getSqlSession();
    	AuthorEnumMapper authorMapper = sqlSession.getMapper(AuthorEnumMapper.class);
    	
    	Author author = new Author().withUsername("alice1")
    			.withPassword("alice123")
    			.withEmail("alice@mybatis.com")
    			.withCurrency(CurrencyEnum.USA.name()); // 注意：此处是修改了Author中的current属性为枚举类型
    	authorMapper.insertEnum(author);
    	
    	// 必须提交sqlSession，否则数据是不会插入成功的！
    	sqlSession.commit();
    	sqlSession.close();
    	
    	Assert.assertTrue(author.getId() > 0);
    }
    
	/**
	 * 自定义TypeHandler将结果映射为枚举实例
	 */
    @Test
    public void test02_selectEnum() throws Exception {
    	SqlSession sqlSession = MybatisUtil.getSqlSession();
    	AuthorEnumMapper authorMapper = sqlSession.getMapper(AuthorEnumMapper.class);
    	Author author = authorMapper.selectByPrimaryKeyEnum(1);
    	
    	// 必须提交sqlSession，否则数据是不会插入成功的！
    	sqlSession.commit();
    	sqlSession.close();
    	
    	System.out.println(author);
    	
    	Assert.assertTrue(author.getId() > 0);
    }
    
    
}
