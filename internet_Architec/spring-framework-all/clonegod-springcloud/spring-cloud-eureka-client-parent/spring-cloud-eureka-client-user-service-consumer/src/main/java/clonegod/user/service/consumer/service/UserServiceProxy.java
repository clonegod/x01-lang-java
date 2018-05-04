package clonegod.user.service.consumer.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import clonegod.user.domain.User;
import clonegod.user.service.UserService;

/**
 * 用户服务的代理类
 *
 */
@Service
public class UserServiceProxy implements UserService {

	// spring-cloud-eureka-client-user-service-provider 中所配置的应用名称（spring.application.name）
	private static final String USER_SERVICE_URL_PREFIX = "http://user-service-provider";
	
	/**
	 * 通过REST API 代理到服务提供者
	 */
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public boolean saveUser(User user) {
		User returnValue =
			restTemplate.postForObject(
					String.join("/", USER_SERVICE_URL_PREFIX, "user/save"), 
					user, 
					User.class);
		
		user.setId(returnValue.getId());
		
		return returnValue != null;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public Collection<User> findAll() {
		return restTemplate.postForObject(
				String.join("/", USER_SERVICE_URL_PREFIX, "user/list"), 
				null,
				Collection.class);
	}
	
}
