package clonegod.framework.dal.plugins;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import lombok.extern.slf4j.Slf4j;

/**
 MyBatis allows you to intercept calls to at certain points within the execution of a mapped statement. 
 By default, MyBatis allows plug-ins to intercept method calls of:

Mybatis plugin 只支持特定操作的拦截
	Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
	ParameterHandler (getParameterObject, setParameters)
	ResultSetHandler (handleResultSets, handleOutputParameters)
	StatementHandler (prepare, parameterize, batch, update, query)
 */

//  拦截Executor执行的query方法
@Intercepts({@Signature(type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
@Slf4j
public class ClonegodPrintQuerySQLPlugin implements Interceptor {
	
	private boolean printSQL = true;
	
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        BoundSql boundSql = mappedStatement.getBoundSql(invocation.getArgs()[1]);
        log.info("-------------------------------------printSQL:{}", this.printSQL);
        if(printSQL) {
        	log.info("plugin output sql = {}\nparams={}",
        			boundSql.getSql(),  boundSql.getParameterObject());
        }
        return invocation.proceed();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
//    	System.out.println(Arrays.toString(properties.keySet().toArray()));
    	// 获取外部的配置，设置到全局变量中
    	this.printSQL = Boolean.valueOf(properties.getProperty("printSQL", "true"));
    }
}
