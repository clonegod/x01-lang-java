package clonegod.mybatis.v2;

import clonegod.mybatis.dao.Author;
import clonegod.mybatis.dao.AuthorMapper;
import clonegod.mybatis.v2.config.CGConfiguration;
import clonegod.mybatis.v2.executor.ExecutorFactory;
import clonegod.mybatis.v2.executor.ExecutorType;
import clonegod.mybatis.v2.session.CGSqlSession;

public class Bootstrap {
	
	public static void main(String[] args) throws Exception {
		
		CGConfiguration cfg = new CGConfiguration();
		cfg.setBasePackage("clonegod.mybatis.dao");
		cfg.build();
		
		CGSqlSession sqlSession = new CGSqlSession(cfg, ExecutorFactory.get(ExecutorType.SIMPLE, cfg));
//		CGSqlSession sqlSession = new CGSqlSession(cfg, ExecutorFactory.get(ExecutorType.CACHING, cfg));
		
		AuthorMapper authorMapper = sqlSession.getMapper(AuthorMapper.class);
		
		Author author = authorMapper.selectByPrimaryKey(1);
		System.out.println(author);
		
		System.out.println();
		
		author = authorMapper.selectByPrimaryKey(1);
		System.out.println(author);
	}
}
