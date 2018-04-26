package clonegod.mybatis.v2.mapper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import clonegod.mybatis.v2.config.CGMapperRegistry;
import clonegod.mybatis.v2.session.CGSqlSession;

public class CGMapperProxy implements InvocationHandler {
	private final CGSqlSession sqlSession;

	public CGMapperProxy(CGSqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	@SuppressWarnings("all")
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		CGMapperRegistry.MapperData mapperData =
                sqlSession.getConfiguration()
                        .getMapperRegistry()
                        .get(method.getDeclaringClass().getName() + "." + method.getName());
		if (null != mapperData) {
            System.out.println(String.format("SQL [ %s ], parameter [%s] ", mapperData.getSql(), args[0]));
            return sqlSession.selectOne(mapperData, String.valueOf(args[0]));
        }
        return method.invoke(this, args);
	}

}
