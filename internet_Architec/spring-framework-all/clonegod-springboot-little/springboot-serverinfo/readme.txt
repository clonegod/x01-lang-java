Server基础配置
	server.port
	servet.context-path
	server.tomcat.xxx
	
Server运行状态监控
	spring-boot-starter-actuator

# 关闭actuator的受限控制，否则访问报401
management.security.enabled=false

http://localhost:9090/app/health
http://localhost:9090/app/metrics
http://localhost:9090/app/env
http://localhost:9090/app/mappings
。。。
	
see more at: 
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready


http://blog.didispace.com/spring-boot-actuator-1/
http://www.baeldung.com/spring-boot-actuators
https://aboullaite.me/an-introduction-to-spring-boot-actuator/


