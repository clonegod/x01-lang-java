package com.clonegod.webflux;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.clonegod.api.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class RouterFunctionTest {
	
	@Autowired
	TestRestTemplate testRestTemplate; // TestRestTemplate 请求的路径只需要写URI即可，不用关系context-path，也不需要写绝对的请求路径。
	
	@LocalServerPort
//	@Value("${local.server.port}")
	private int port;
	
	@Test
	public void testSaveAndFind() {
		User user = User.builder().name("alice-3").build();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
		ResponseEntity<Boolean> resEntity = testRestTemplate.exchange(
				"/user/save", 
				HttpMethod.POST, requestEntity, Boolean.class);
		System.out.println(resEntity.getBody());
		
		
		String users1 = testRestTemplate.getForObject("/all-users", String.class);
		System.out.println(users1);
		
		String users2 = testRestTemplate.getForObject("/users", String.class);
		System.out.println(users2);
		
	}
	
}
