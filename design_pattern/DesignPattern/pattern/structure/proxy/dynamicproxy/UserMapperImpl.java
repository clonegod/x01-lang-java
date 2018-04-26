package dynamicproxy;

public class UserMapperImpl implements UserMapper {

	@Override
	public Object selectOne(String key) {
		System.out.println("UserMapperImpl run ... "
				+ "\n\t 1.根据接口包名和调用方法名，从mapper.xml中寻找对应的sql；"
				+ "\n\t 2.参数绑定；"
				+ "\n\t 3.执行sql；"
				+ "\n\t 4.结果映射为bean");
		return "Alice";
	}

}
