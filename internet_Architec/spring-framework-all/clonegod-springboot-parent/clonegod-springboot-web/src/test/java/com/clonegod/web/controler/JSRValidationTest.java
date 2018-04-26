package com.clonegod.web.controler;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.clonegod.validation.Bean;
import com.clonegod.web.SpringBootTestBase;

public class JSRValidationTest extends SpringBootTestBase {

	@Test
	public void testJsr303Validation() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/json"));

		Bean bean = Bean.builder()
						.id(101)
						.name("alice")
						.cardNumber("clonegod-123456")
						.build();

		HttpEntity<Bean> requestEntity = new HttpEntity<>(bean, headers);
		ResponseEntity<String> resEntity = 
				testRestTemplate.exchange("/validate/bean/save", 
											HttpMethod.POST,
											requestEntity, 
											String.class);
		
		System.out.println(resEntity.getBody());
		
		
		
	}

}
