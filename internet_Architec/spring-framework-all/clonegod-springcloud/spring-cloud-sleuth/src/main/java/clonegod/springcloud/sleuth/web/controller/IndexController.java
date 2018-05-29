package clonegod.springcloud.sleuth.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("")
	public String index() {
		String value = "sleuth, Zipkin";
		logger.info("{} index(), return: {}", this.getClass().getSimpleName(), value);
		return value;
	}
	
}
