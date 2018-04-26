package com.clonegod.fastjson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	// 配置@SpringBootTest使用指定端口或随机端口时，可直接注入TestRestTemplate使用
	@Autowired
    private TestRestTemplate template;
	
	@Test
	public void contextLoads() {
	}
	
    @Test
    public void testRequest() throws Exception {
    	// 相对于应用根路径下的子路径-> controller中配置的路径
    	String apiPath = "/fastjson/user"; 
        String result = template.getForEntity(apiPath, String.class).getBody();
        System.out.println(result);
    }

}
