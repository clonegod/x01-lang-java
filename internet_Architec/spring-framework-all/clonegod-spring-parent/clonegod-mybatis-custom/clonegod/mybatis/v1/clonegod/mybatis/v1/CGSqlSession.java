package clonegod.mybatis.v1;
/**
 * mybatis执行流程主干：
 * 1、找到SQL	-	Configuration
 * 2、接口代理     - 	MapperProxy
 * 2、设置参数	-	ParameterHandler
 * 3、执行SQL	- 	Executor
 * 4、映射结果	- 	ResultHandler
 * 
 * sqlSession 提供操作入口，内部将操作委托给相关对象，最终实现jdbc操作。
 */
public class CGSqlSession {

	/**
	 * sqlSession 是操作的主线，内部组合了Configuration、Executor
	 * Configuration 负责读取并解析xml文件中编写的sql
	 * Executor 负责执行sql
	 */
	private CGConfiguration configuration;
	private CGExecutor executor;
	
	public CGSqlSession(CGConfiguration configuration, CGExecutor executor) {
		super();
		this.configuration = configuration;
		this.executor = executor;
	}

	public <T> T getMapper(Class<T> clazz) {
		return configuration.getMapper(clazz, this);
	}
	

	public <T> T selectOne(String statement, Object parameter) {
		return executor.query(statement, parameter);
	} 
	
}
