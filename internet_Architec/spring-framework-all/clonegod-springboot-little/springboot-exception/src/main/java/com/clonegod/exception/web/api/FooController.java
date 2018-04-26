package com.clonegod.exception.web.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clonegod.exception.ex.RestApiException;

@RestController
public class FooController implements RestApi {

	@RequestMapping("/error/foo")
	public void home() {
		try {
			int i = 1 / 0;
		} catch (Exception e) {
			throw new RestApiException("Opps-Error Occur!", e);
		}
	}
	
}
