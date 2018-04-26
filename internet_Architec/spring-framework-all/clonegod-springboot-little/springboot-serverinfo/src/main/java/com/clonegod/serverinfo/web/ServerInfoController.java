package com.clonegod.serverinfo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerInfoController {
	
	@Autowired
	Environment serverEnv;

    @RequestMapping("/")
    String serverInfo() {
        return "<a href='https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready'>Spring Boot Actuator</a>";
    }

}