package dynamicproxy;

public class TestDynamicProxy {
	
	public static void main(String[] args) {
		
		// 将实例对象注入到代理类中
		MapperProxy mapperProxy = new MapperProxy(new UserMapperImpl());
		
		// 得到代理对象
		UserMapper userMapper = mapperProxy.getMapper();
		
		// 使用代理对象执行方法
		System.out.println("result: " + userMapper.selectOne(""));
	}
}
