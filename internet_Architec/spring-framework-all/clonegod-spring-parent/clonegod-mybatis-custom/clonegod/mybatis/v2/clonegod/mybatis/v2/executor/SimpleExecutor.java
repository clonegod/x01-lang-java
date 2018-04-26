package clonegod.mybatis.v2.executor;

import clonegod.mybatis.v2.config.CGConfiguration;
import clonegod.mybatis.v2.config.CGMapperRegistry.MapperData;
import clonegod.mybatis.v2.statement.CGStatementHandler;
import clonegod.mybatis.v2.statement.StatementHandler;

public class SimpleExecutor implements CGExecutor {
    private CGConfiguration configuration;

    public SimpleExecutor(CGConfiguration configuration) {
        this.configuration = configuration;
    }

    public CGConfiguration getConfiguration() {
        return configuration;
    }

	@Override
	public <T> T query(MapperData<T> mapperData, Object parameter) throws Exception {
		//初始化StatementHandler --> ParameterHandler --> ResultSetHandler
		StatementHandler statementHandler = new CGStatementHandler(configuration);
        
		/**
		 * 将代理对象进行强制类型转换时，必须用接口来引用。不能用具体实现类！
		 * java.lang.ClassCastException: com.sun.proxy.$Proxy3 cannot be cast to clonegod.mybatis.v2.statement.CGStatementHandler
		 */
//		CGStatementHandler statementHandlerProxy = 
//        		(CGStatementHandler) configuration.getInterceptorChain().pluginAll(statementHandler);
		StatementHandler statementHandlerProxy = 
				(StatementHandler) configuration.getInterceptorChain().pluginAll(statementHandler);
        
        return (T) statementHandlerProxy.query(mapperData, parameter);
	}
}