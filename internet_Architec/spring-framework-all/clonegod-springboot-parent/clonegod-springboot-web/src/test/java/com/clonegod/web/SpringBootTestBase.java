package com.clonegod.web;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class SpringBootTestBase {
	
	// 配置了 webEnvironment=WebEnvironment.RANDOM_PORT 之后，请求URI向相对路径即可
	@Autowired
	protected TestRestTemplate testRestTemplate;  
	
	@LocalServerPort
//	@Value("${local.server.port}")
	protected int port;
	
	
}
