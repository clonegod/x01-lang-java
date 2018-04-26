package com.clonegod.web.controler;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.clonegod.api.User;
import com.clonegod.web.SpringBootTestBase;

public class UserControllerTest extends SpringBootTestBase {
	
	@Test
	public void testMvc() {
		for(int i = 0; i < 3; i++) {
			User user = User.builder().name("alice-" + i).build();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
			ResponseEntity<User> resEntity = testRestTemplate.exchange(
					"/user/save", 
					HttpMethod.POST, requestEntity, User.class);
			System.out.println(resEntity.getBody());
		}
		
		System.out.println("========================\n");
		
		String user = testRestTemplate.getForObject("/user/1", String.class);
		System.out.println(user);
		
	}
	
	@Test
	public void testCustomerHttpMessageConverter_propToJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/properties+user"));
		headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));
		
		String body = "user.id=1\nuser.name=alcie";
		HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
		ResponseEntity<User> resEntity = testRestTemplate.exchange(
				"/rest/user/prop/to/json", 
				HttpMethod.POST, requestEntity, User.class);
		System.out.println(resEntity.getBody());
		
	}
	
	@Test
	public void testCustomerHttpMessageConverter_jsonToProp() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Lists.newArrayList(MediaType.valueOf("application/properties+user")));
		
		User user= User.builder().id(123L).name("bob").build();
		HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
		ResponseEntity<String> resEntity = testRestTemplate.exchange(
				"/rest/user/json/to/prop", 
				HttpMethod.POST, requestEntity, String.class);
		System.out.println(resEntity.getBody());
		
	}
	
}
