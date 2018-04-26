package clonegod.mybatis.v2.session;

import java.lang.reflect.Proxy;

import clonegod.mybatis.v2.config.CGConfiguration;
import clonegod.mybatis.v2.config.CGMapperRegistry;
import clonegod.mybatis.v2.executor.CGExecutor;
import clonegod.mybatis.v2.mapper.CGMapperProxy;

public class CGSqlSession {
    private CGConfiguration configuration;
    private CGExecutor executor;
    
    //关联起来
    public CGSqlSession(CGConfiguration configuration, CGExecutor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    public CGConfiguration getConfiguration() {
        return configuration;
    }

    @SuppressWarnings("unchecked")
	public <T> T getMapper(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz}, 
                new CGMapperProxy(this));
    }

    public <T> T selectOne(CGMapperRegistry.MapperData<T> mapperData, Object parameter) throws Exception {
        return executor.query(mapperData, parameter);
    }
}
