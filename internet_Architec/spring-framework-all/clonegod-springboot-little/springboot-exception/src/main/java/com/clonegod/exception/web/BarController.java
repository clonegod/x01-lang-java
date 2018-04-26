package com.clonegod.exception.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clonegod.exception.ex.RestApiException;

@RestController
public class BarController {

	@RequestMapping("/error/bar")
	public void home() {
		try {
			int i = 1 / 0;
		} catch (Exception e) {
			throw new RestApiException("Opps-Error Occur!", e);
		}
	}
	
}
