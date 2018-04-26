package clonegod.mybatis.v2.config;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clonegod.mybatis.v2.annotation.CGSelect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class CGMapperRegistry {
	
	String mapperPath;
	
	public static final Map<String, MapperData<?>> methodSqlMapping = new HashMap<>();
	
	/**
	 * 扫描mapper所在package，将sql和方法注册到Map中
	 * 
	 * @param mapperPath
	 */
	public void registry(String mapperPath) {
		this.mapperPath = mapperPath;
		initMethodSqlMap();
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@ToString
	public class MapperData<T>{
        private String sql;
        private Class<T> type;
    }

    public MapperData<?> get(String nameSpace) {
        return methodSqlMapping.get(nameSpace);
    }
	
	private void initMethodSqlMap() {
		try {
			List<Class> clzList = ClassPathHelper.getClasses(mapperPath);
			clzList.stream().filter(x -> x.isInterface()).forEach( c -> {
				Method[] methods = c.getDeclaredMethods();
				for(Method m : methods) {
					if(m.isAnnotationPresent(CGSelect.class)) {
						String sql = m.getAnnotation(CGSelect.class).value();
						methodSqlMapping.put(
								m.getDeclaringClass().getName() + "." + m.getName(), 
								new MapperData<>(sql, m.getReturnType()));
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(methodSqlMapping);
	}
	
}
