package clonegod.mybatis.v1;

public interface CGExecutor {
	
	<T> T query(String statement, Object parameter);
	
}
