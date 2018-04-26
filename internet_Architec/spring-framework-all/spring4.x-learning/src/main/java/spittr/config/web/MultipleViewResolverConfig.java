package spittr.config.web;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.xml.MarshallingView;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.thoughtworks.xstream.io.xml.StaxDriver;

import spittr.domain.Spittle;

/**
 * 配置多视图解析器
 * 
 */
@Configuration
public class MultipleViewResolverConfig {
	
	// 视图解析器的order属性根据枚举ordinal排序，因此，下面枚举的顺序决定了ViewResolver的顺序！
	// ViewNameInterceptor提供对多视图的支持
	public enum VIEW_RESOLVERS_TYPE {
		XmlViewResolver, // 返回xml内容视图
		JsonViewResolver, // 返回json内容视图
		BeanNameViewResolver,  // excel , pdf 特殊类型，专门配置一个Bean进行视图处理
		UrlBasedViewResolver,
		ThymeleafViewResolver,
		InternalResourceViewResolver,
		MultipartResolver;
	}
	
	/************************************************************************
	 * BeanNameViewResolver
	 * 	- excel
	 *  - pdf
	 *************************************************************************
	 */
	@Bean
	public ViewResolver beanNameViewResolver() {
		BeanNameViewResolver resolver = new BeanNameViewResolver();
		resolver.setOrder(VIEW_RESOLVERS_TYPE.BeanNameViewResolver.ordinal());
		return resolver;
	}
	
	/************************************************************************
	 * XMLView
	 *************************************************************************
	 */
	public static final String XML_VIEW = "xmlView";
	@Bean(name="xmlView")
	public MarshallingView xmlViewResolver() {
		MarshallingView xmlView = new MarshallingView();
		xmlView.setModelKey("data");
		
		XStreamMarshaller marshaller = new XStreamMarshaller();
		marshaller.setStreamDriver(new StaxDriver());
		marshaller.setAutodetectAnnotations(true);
		marshaller.setAnnotatedClasses(Spittle.class);
		
		xmlView.setMarshaller(marshaller);
		return xmlView;
	}
	
	/************************************************************************
	 * JsonView
	 *************************************************************************
	 */
	public static final String JSON_VIEW = "jsonView";
	@Bean(name="jsonView")
	public MappingJackson2JsonView jsonViewResolver() {
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setModelKey("data");
		jsonView.setPrettyPrint(true);
		return jsonView;
	}

	/************************************************************************
	 * THYMELEAF VIEW RESOLVER
	 *************************************************************************
	 */
	@Bean
	public ViewResolver viewResolver(SpringTemplateEngine templateEngine){
	    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
	    viewResolver.setTemplateEngine(templateEngine);
	    viewResolver.setOrder(VIEW_RESOLVERS_TYPE.ThymeleafViewResolver.ordinal());
	    viewResolver.setViewNames(new String[] {"thymeleaf/*"}); // 指定视图名称的模式，只有匹配该模式的逻辑视图才由本Resolver处理
	    viewResolver.setCharacterEncoding("UTF-8");
	    System.out.println("create:viewResolver");
	    return viewResolver;
	}
	
	
	@Bean
	public SpringTemplateEngine templateEngine(TemplateResolver templateResolver){
	    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	    templateEngine.setTemplateResolver(templateResolver);
	    System.out.println("create:templateEngine");
	    return templateEngine;
	}
	

	@Bean
	public TemplateResolver templateResolver(){
	    TemplateResolver templateResolver = new ServletContextTemplateResolver(); // new ServletContextTemplateResolver();
	    templateResolver.setPrefix("/WEB-INF/views/");
	    templateResolver.setSuffix(".html");
	    templateResolver.setCharacterEncoding("UTF-8");
	    templateResolver.setTemplateMode("HTML5");
	    System.out.println("create:templateResolver");
	    return templateResolver;
	}
	
	
	/************************************************************************
	 * JSP VIEW RESOLVER
	 *************************************************************************
	 */
	@Bean
	public ViewResolver jspViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class); // 使用JSTL标签来处理格式化和信息
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setViewNames("jsp/*"); // 指定视图名称的模式，只有匹配该模式的逻辑视图才由本Resolver处理 
		resolver.setSuffix(".jsp");
		resolver.setOrder(VIEW_RESOLVERS_TYPE.InternalResourceViewResolver.ordinal());
		return resolver;
	}
	
	/************************************************************************
	 * URLBASED VIEW RESOLVER
	 * 	thymeleafResolver与internalResolver不再处理redirect，因此需要提供一个支持 redirect:xxx的视图解析器作为后备
	 *************************************************************************
	 */
	@Bean
	public ViewResolver urlViewResolver() {
		UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
		viewResolver.setOrder(VIEW_RESOLVERS_TYPE.UrlBasedViewResolver.ordinal());
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setViewNames("redirect:*");
		return viewResolver;
	}
	
	
	
	/************************************************************************
	 * MultipartResolver
	 * 	support file upload
	 *************************************************************************
	 */
	public MultipartResolver multipartResolver() throws IOException {
		return new StandardServletMultipartResolver();
	}
	
	
	
}
