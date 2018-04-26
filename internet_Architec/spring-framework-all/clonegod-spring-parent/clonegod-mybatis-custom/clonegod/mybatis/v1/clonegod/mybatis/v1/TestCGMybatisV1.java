package clonegod.mybatis.v1;

import clonegod.mybatis.dao.Author;
import clonegod.mybatis.dao.AuthorMapper;

public class TestCGMybatisV1 {
	public static void main(String[] args) {
		CGSqlSession sqlSession = new CGSqlSession(new CGConfiguration(), new CGSimpleExecutor());
		AuthorMapper testMapper = sqlSession.getMapper(AuthorMapper.class);
		Author author = testMapper.selectByPrimaryKey(1);
		System.out.println(author);
	}
}
