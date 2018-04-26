package clonegod.mybatis.v1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CGMapperProxy implements InvocationHandler {

	CGSqlSession sqlSession;
	
	public CGMapperProxy(CGSqlSession sqlSession) {
		super();
		this.sqlSession = sqlSession;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		/**
		 * MapperProxy要完成的核心工作：根据调用方法的方法名，找到对应的sql语句，交给sqlSession去执行.
		 */
		String className = method.getDeclaringClass().getName();
		// 判断是否匹配 namespace
		if(className.equals(CGConfiguration.TestMapperXML.namespace)) {
			String sql = CGConfiguration.TestMapperXML.methodSqlMapping.get(method.getName());
			return sqlSession.selectOne(sql, args[0]); // 实际是委托给了executor去执行sql
		}
		
		return method.invoke(this, args);
	}

}
