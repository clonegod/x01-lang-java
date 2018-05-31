package clonegod.springcloud.sleuth.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class IndexController {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private RestTemplate restTemplate;

	@Autowired
	public IndexController(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	@GetMapping("")
	public String index() {
		String value = "sleuth, Zipkin";
		logger.info("{} index(), return: {}", this.getClass().getSimpleName(), value);
		return value;
	}
	
	/**
	 * 完整调用链路:
	 * 	浏览器
	 * 		-> sleuth 
	 * 		-> zuul 
	 * 		-> person-client 
	 * 		-> person-service 	
	 */
	@GetMapping("/to/zuul")
	public Object toZuul() {
		String zuulServiceName = "spring-cloud-zuul"; 	// zuul在eureka上注册 的名称
		String personServiceName = "person-client"; 	// person-client在eureka上注册 的名称
		String url = "http://" + zuulServiceName + "/" + personServiceName + "/person/list";
		logger.info("to zuul, url={}", url);
		Object value = restTemplate.getForObject(url, Object.class);
		return value;
	}
	
}
