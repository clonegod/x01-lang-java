package com.clonegod.profile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.clonegod.profile.config.FooProperties;
import com.clonegod.profile.config.ServerConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(properties= {"spring.profiles.active="})
//@SpringBootTest(properties= {"spring.profiles.active=test"})
//@SpringBootTest(properties= {"spring.profiles.active=prod"})
public class ApplicationTests {

	@Autowired
	ServerConfig serverConfig;
	
	@Autowired
	FooProperties fooConfig;
	
	@Test
	public void contextLoads() {
		serverConfig.getServers().forEach((x) -> System.out.println("---> server: "+x));
		
		fooConfig.getList().forEach((x) -> System.out.println("---> " + x.toString()));
	}

}
