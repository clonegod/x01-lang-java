package clonegod.mybatis.v2.executor;

import clonegod.mybatis.v2.config.CGMapperRegistry;

public interface CGExecutor {

	<T> T query(CGMapperRegistry.MapperData<T> mapperData, Object parameter) throws Exception;

}
