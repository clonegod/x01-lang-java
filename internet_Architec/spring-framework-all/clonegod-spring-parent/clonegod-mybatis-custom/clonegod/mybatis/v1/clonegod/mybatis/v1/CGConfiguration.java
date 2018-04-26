package clonegod.mybatis.v1;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuration中，除了解析xml文件中的sql外，还有一个重要的功能，就是返回Mapper接口的代理对象。
 * 
 * Mapper接口的代理对象在执行的时候，会在invoke方法中，根据当前执行方法的name在内存中找到对应的sql，然后将该sql语句交给sqlSession去执行。
 *
 */
public class CGConfiguration {
	
	/**
	 * 返回Mapper接口的代理
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getMapper(Class<T> interfaceCls, CGSqlSession sqlSeesion) {
		return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), 
				new Class[] {interfaceCls}, 
				// 代理对象
				new CGMapperProxy(sqlSeesion));
	}
	
	/**
	 * 模拟从xml文件解析得到sql
	 *
	 */
	static class TestMapperXML {
		public static final String namespace = "clonegod.mybatis.dao.AuthorMapper";
		// mapper接口中的方法名与xml文件中sql的id一一对应
		public static final Map<String, String> methodSqlMapping = new HashMap<>();
		static {
			methodSqlMapping.put("selectByPrimaryKey", "select * from author where id = ?");
		}
	}

}
