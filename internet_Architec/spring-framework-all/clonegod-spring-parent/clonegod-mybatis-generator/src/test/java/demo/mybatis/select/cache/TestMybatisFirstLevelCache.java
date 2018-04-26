package demo.mybatis.select.cache;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import clonegod.framework.dal.dao.Author;
import clonegod.framework.dal.dao.AuthorMapper;
import clonegod.mybatis.utils.MybatisUtil;

public class TestMybatisFirstLevelCache {
	 /**
     * 验证一级缓存在同一个session实例上是可见的
     */
    @Test
    public void test01_firstLevelCacheForSameSession() throws Exception {
    	SqlSession sqlSession = MybatisUtil.getSqlSession();
        AuthorMapper authorMapper = sqlSession.getMapper(AuthorMapper.class);
        
        int authorId = getMaxAuthorId();
        
        long start = System.currentTimeMillis();
        Author author = authorMapper.selectByPrimaryKey(authorId);
        Assert.assertNotNull(author);
        System.out.println("cost:" + (System.currentTimeMillis() - start));
        
        start = System.currentTimeMillis();
        author = authorMapper.selectByPrimaryKey(authorId);
        System.out.println("cost:" + (System.currentTimeMillis() - start));
        
        // 修改数据库的记录后，在Mapper上绑定的一级缓存失效
        if(Math.random() > 0.5) {
        	System.err.println("=======>>>数据发生更新，导致一级缓存失效");
        	author.withUsername("alice"+String.valueOf(new Random().nextInt(100)));
        	authorMapper.updateByPrimaryKey(author);
        }
        
        start = System.currentTimeMillis();
        author = authorMapper.selectByPrimaryKey(authorId);
        System.out.println("cost:" + (System.currentTimeMillis() - start));
        
        sqlSession.commit();
    	sqlSession.close();
    }

    /**
     * 验证一级缓存在不同session实例上是不可见的
     * 结论： 一级缓存是绑定在sqlsession上的！
     */
    @Test
    public void test02_firstLevelCacheForDifferSession() throws Exception {
        SqlSession sqlSession1 = MybatisUtil.getSqlSessionFactory().openSession();
        SqlSession sqlSession2 = MybatisUtil.getSqlSessionFactory().openSession();
        SqlSession sqlSession3 = MybatisUtil.getSqlSessionFactory().openSession();
        
        int authorId = getMaxAuthorId();
        
        getOne(sqlSession1, authorId);
        sqlSession1.commit();
        sqlSession1.close();
        
        getOne(sqlSession2, authorId);
        sqlSession2.commit();
        sqlSession2.close();
        
        getOne(sqlSession3, authorId);
        sqlSession3.commit();
        sqlSession3.close();
    }
    
    private void getOne(SqlSession sqlSession, int id) throws SQLException, FileNotFoundException {
    	AuthorMapper authorMapper = sqlSession.getMapper(AuthorMapper.class);
        long start = System.currentTimeMillis();
        authorMapper.selectByPrimaryKey(id);
        System.out.println("cost:" + (System.currentTimeMillis() - start));
    }
    
    private int getMaxAuthorId() throws Exception {
    	SqlSession sqlSession = MybatisUtil.getSqlSession();
    	Connection rawConnection = sqlSession.getConnection();
        PreparedStatement psmt = rawConnection.prepareStatement("SELECT MAX(id) FROM author");
        ResultSet rs = psmt.executeQuery();
        if(rs.next()) {
        	int id = rs.getInt(1);
        	sqlSession.close();
        	return id;
        }
        throw new RuntimeException("table is empty!");
    }
}
