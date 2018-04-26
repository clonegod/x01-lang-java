package spittr.config;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import spittr.config.web.AspectConfig;
import spittr.config.web.MultipleViewResolverConfig;
import spittr.web.interceptor.LoggingInterceptor;
import spittr.web.interceptor.ViewNameInterceptor;

@Configuration	// 声明该类为一个配置类
@EnableWebMvc	// 启用Spring MVC组件
@Import(value= {MultipleViewResolverConfig.class, AspectConfig.class})
@ComponentScan("spittr.web")	// 启用组件扫描，配置web相关的Controller组件
public class WebConfig extends WebMvcConfigurerAdapter {

  /*******************************************************************************
   * 配置静态资源的处理方式
   * 要求DispatcherServlet将对静态资源的请求转发到Servlet容器中默认的Servlet上， 而不是使用DispatcherServlet本身来处理此类请求。
   * 
   * ******************************************************************************
   */
  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
	  registry.addResourceHandler("/static/**"); // ???
  }
  
  
  
  
  /******************************************************************************
   * 配置ResourceBundleMessageSource加载资源文件
   * 好处：
   * 1. 将硬编码的信息抽取到了属性文件中。 它能够让我们一站式地修改应用中的所有信息，比如JSP中硬编码的一些文字内容。
   * 2. 具备了对信息进行国际化的重要组成部分
   * 	比如，再添加一个message_en.properties提供英文支持
   * 
   *******************************************************************************
   */
  @Bean
  public MessageSource messageSource() {
	  // 支持重新加载信息属性， 而不必重新编译或重启应用
	  ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	  // basename属性可以设置为在类路径下（以“classpath:”作为前缀） 、 文件系统中（以“file:”作为前缀） 或Web应用的根路径下（没有前缀） 查找属性
	  messageSource.setBasename("classpath:messages"); // 不要加后缀.properties!!!
	  messageSource.setCacheSeconds(3);
	  return messageSource;
  }
  
  @Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(new LoggingInterceptor());
	    registry.addInterceptor(new ViewNameInterceptor()).addPathPatterns("/**");
	}
  
  
  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
      final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
      final ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
      objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // pretty print
      converter.setObjectMapper(objectMapper);
      converters.add(converter);
      super.configureMessageConverters(converters);
  }
}
