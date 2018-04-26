package clonegod.mybatis.v2.plugin;

import clonegod.mybatis.v2.annotation.PluginPoint;
import clonegod.mybatis.v2.config.CGMapperRegistry.MapperData;
import clonegod.mybatis.v2.plugin.proxy.Invocation;
import clonegod.mybatis.v2.plugin.proxy.PluginProxy;
import clonegod.mybatis.v2.statement.StatementHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 拦截器 ：在某个地方被拦截，然后执行intercept方法
 * 
 * @author clonegod
 *
 */
@Slf4j
@PluginPoint(StatementHandler.class)
public class PrintSQLEnglishPlugin implements Interceptor {
	
	private boolean printSQL = true;
	
	@Override
    public Object intercept(Invocation invocation) throws Throwable {
		MapperData mapperData = (MapperData) invocation.getArgs()[0];
    	String sql = mapperData.getSql();
    	String params = (String) invocation.getArgs()[1];
    	System.out.println("\n=====================PrintSQLEnglishPlugin====================");
        log.info("-------------------------------------printSQL:{}", this.printSQL);
        if(printSQL) {
        	log.info("intercept output sql = {}\nparams={}",
        			sql,  params);
        }
        // 执行executor/xxxhandler的方法
        return invocation.proceed();
    }

	/**
	 * 
	 */
	@Override
    public Object plugin(Object target) {
        return PluginProxy.proxy(target, this);
    }
    
}
