package com.asynclife.hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // @RestController combines @Controller and @ResponseBody
public class HelloController {
	
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}