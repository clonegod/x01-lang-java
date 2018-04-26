package clonegod.mybatis.v2.statement;

import clonegod.mybatis.v2.config.CGMapperRegistry;

public interface StatementHandler {
	
	public <T> T query(CGMapperRegistry.MapperData<T> mapperData, Object parameter) throws Exception;
	
}
