package clonegod.dubbox.restful.impl;

import org.springframework.stereotype.Service;

import clonegod.dubbox.restful.api.IUserService;
import clonegod.dubbox.restful.api.User;


/**这个是spring的注解*/
@Service("userService")

/**这个是dubbo的注解（同时提供dubbo本地，和rest方式）- 也可以在配置文件中进行配置*/
@com.alibaba.dubbo.config.annotation.Service(
		interfaceClass=clonegod.dubbox.restful.api.IUserService.class, 
		protocol = {"rest", "dubbo"}, 
		retries=0)
public class UserServiceImpl implements IUserService{
	
	@Override
	public void testGet() {
		//http://localhost:8888/provider/userService/testget
		System.out.println("测试...get");
	}
	
	@Override
	public User getUser() {
    	User user = new User();
    	user.setId("1001");
    	user.setName("张三1");
    	return user;
	}

	@Override
	public User getUser(Integer id) {
		System.out.println("测试传入int类型的id: " + id);
    	User user = new User();
    	user.setId("1002");
    	user.setName("张三2");
    	return user;
	}

	@Override
	public User getUser(Integer id, String name) {

		System.out.println("测试两个参数：");
		System.out.println("id: " + id);
		System.out.println("name: " + name);
    	User user = new User();
    	user.setId("1003");
    	user.setName("张三3");
    	return user;
	}

	@Override
	public void testPost() {
    	System.out.println("测试...post");
	}
    
	@Override
	public User postUser(User user) {
    	System.out.println(user.getName());
    	System.out.println("测试...postUser");
    	User user1 = new User();
    	user1.setId("1005");
    	user1.setName("张三5");
    	return user1;
	}

	@Override
	public User postUser(String id) {
		System.out.println(id);
		System.out.println("测试...postUser");
    	User user = new User();
    	user.setId("1006");
    	user.setName("张三6");
    	return user;
	}

	@Override
	public User testPut(User user) {
		System.out.println("测试...testPut");
		System.out.println(user.toString());
    	user.setId("1007");
    	user.setName("张三7");
    	return user;
	}

	@Override
	public boolean testDelete(int id) {
		System.out.println("测试...testPut");
		System.out.println("id="+id);
		return id > 10;
	}
	
}
