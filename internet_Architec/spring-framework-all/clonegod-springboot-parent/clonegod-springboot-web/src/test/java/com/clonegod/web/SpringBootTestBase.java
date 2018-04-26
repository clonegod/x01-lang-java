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
	
	@Autowired
	protected TestRestTemplate testRestTemplate; // 只需要写请求URI，不需要写完整的请求路径（主机、端口、contextPath）
	
	@LocalServerPort
//	@Value("${local.server.port}")
	protected int port;
	
	
}
