package com.asynclife.hello;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 *  https://spring.io/guides/gs/spring-boot/#scratch
 *  
 *  Creating an executable jar 
 *  在命令行启动
 *  	1. 打包
 *  	2. 运行可执行jar
 * 	
 * 	mvn package -Pfastinstall && java -jar target/spring-boot-0.0.1-SNAPSHOT.jar
 * 
 * @author Administrator
 *
 */


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
    	
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }

}