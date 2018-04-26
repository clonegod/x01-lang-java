package clonegod.mybatis.v2.plugin;

import clonegod.mybatis.v2.annotation.PluginPoint;
import clonegod.mybatis.v2.config.CGMapperRegistry.MapperData;
import clonegod.mybatis.v2.plugin.proxy.Invocation;
import clonegod.mybatis.v2.plugin.proxy.PluginProxy;
import clonegod.mybatis.v2.statement.StatementHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 拦截器 ：代理对象被执行时
 * 
 * @author clonegod
 *
 */
@Slf4j
@PluginPoint(StatementHandler.class)
public class PrintSQLChinesePlugin implements Interceptor {
	
	private boolean printSQL = true;
	
	@Override
    public Object intercept(Invocation invocation) throws Throwable {
		MapperData mapperData = (MapperData) invocation.getArgs()[0];
    	String sql = mapperData.getSql();
    	String params = (String) invocation.getArgs()[1];
    	System.out.println("\n=====================PrintSQLChinesePlugin====================");
        log.info("-------------------------------------printSQL:{}", this.printSQL);
        if(printSQL) {
        	log.info("拦截到sql = {}\n参数={}",
        			sql,  params);
        }
        // 执行target被代理对象的方法
        return invocation.proceed();
    }

	/**
	 * target: Executor or ParameterHandler or ResultSetHandler or StatementHandler 
	 */
	@Override
    public Object plugin(Object target) {
        return PluginProxy.proxy(target, this);
    }
    
}
