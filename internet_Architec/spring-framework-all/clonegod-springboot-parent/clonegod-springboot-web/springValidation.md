## 参数验证

在字段上标记JSR-303注解实现参数校验

JSR-303 标准的实现框架使用Hibernate validator

对于特殊的校验规则，可以自定义注解来提供。

对字符串、数字等进行校验的时候，可以使用相关的工具类：

	spring： StringUtils
	apache： commons提供的 StringUtils, NumberUtils, MathUtils...
	Jdk：	分隔字符串 StringTokenizer 等

-------------
参数校验的几种实现方式：

	第1种：在Controller的方法内部进行参数校验。
		Spring Assert API 对参数进行校验		
		JVM/JAVA 内置的 assert 断言 对参数进行校验
		以上两种方式的缺点：在Controller的方法中耦合了业务逻辑！

	第2种：在Controller的方法调用前，使用拦截器或者Filter进行参数校验。
		创建新的拦截器做参数校验（实现HandlerInterceptor，添加到InterceptorRegistry中）
		使用Filter做拦截，进行参数的校验。
	
	第3种：AOP代理
		通过AOP的方式，在Controller方法被调用前进行参数校验。
	
	第4种： @Validation + JSR303规范

### Spring Validator
	介绍Spring内置验证API，以及自定义扩展实现
	@Validate

	spring-boot-starter-validation
	Starter for using Java Bean Validation with Hibernate Validator	

### Bean Validation (JSR-303)
	介绍Bean的验证，核心API，实现框架：Hibernate Validator 
	@NotNull
	@Max
	...

### Apache commons-validator （使用比较麻烦）
	介绍最传统的Apache通用验证框架，如长度，邮件地址等
	http://commons.apache.org/proper/commons-validator/




