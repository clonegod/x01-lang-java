配置 SprintBoot 使用fastjson作为JSON解析框架
  - 注： spring 默认使用jackson进行json解析

see -> https://docs.spring.io/spring-boot/docs/1.5.8.RELEASE/reference/htmlsingle/#boot-features-spring-mvc-message-converters

1. 注入fastjson到Spring环境中，作为框架使用的HttpMessageConverter
    @Bean
    public HttpMessageConverters customConverters() {
    	FastJsonHttpMessageConverter fastJsonConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fjc = new FastJsonConfig();
		fjc.setSerializerFeatures(
				SerializerFeature.SortField,
				SerializerFeature.PrettyFormat);
		fastJsonConverter.setFastJsonConfig(fjc);
		
        return new HttpMessageConverters(fastJsonConverter);
    }
	
2. 使用@JSONField对实体类字段进行定制，如日期格式化，忽略字段等
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	@JSONField(serialize=false)
	private String remark;
	

--------------------------------------------------------------------------------

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)

1. using the @SpringBootTest annotation with WebEnvironment.RANDOM_PORT or WebEnvironment.DEFINED_PORT, 
	you can just inject a fully configured TestRestTemplate and start using it.
使用@SpringBootTest结合WebEnvironment.RANDOM_PORT,可以直接注入TestRestTemplate发送请求，测试web层api。	
	
2. Any URLs that do not specify a host and port will automatically connect to the embedded server.
	任何被测试的URL，如果没有指定host和端口，则默认将请求发送到内嵌的服务器上（测试时启动的临时运行服务）。
	这里的好处是，测试案例不受host，port的配置，也不用指定context-path
	测试路径为；相对于根路径下的资源URI---即Controller中配置的路径

	



