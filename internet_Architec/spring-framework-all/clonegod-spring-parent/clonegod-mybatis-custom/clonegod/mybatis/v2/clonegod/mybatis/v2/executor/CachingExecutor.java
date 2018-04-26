package clonegod.mybatis.v2.executor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import clonegod.mybatis.v2.config.CGMapperRegistry.MapperData;

public class CachingExecutor implements CGExecutor {

	private SimpleExecutor delegate;
	
	private Map<String, Object> localCache = new ConcurrentHashMap<>();

	public CachingExecutor(SimpleExecutor delegate) {
		this.delegate = delegate;
	}

	@Override
	public <T> T query(MapperData<T> mapperData, Object parameter) throws Exception {
		Object result = localCache.get(mapperData.getSql());
		if(result != null) {
			System.out.println("缓存命中！");
			return (T) result;
		}
		result = delegate.query(mapperData, parameter);
		localCache.put(mapperData.getSql(), result);
		return (T) result;
	}

}
