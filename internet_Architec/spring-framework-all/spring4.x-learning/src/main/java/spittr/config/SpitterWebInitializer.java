package spittr.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * AbstractAnnotationConfigDispatcherServletInitializer会同时创建DispatcherServlet和ContextLoaderListener。 
 * 
 * 自动地配置Dispatcher-Servlet和Spring应用上下文
 * 	ContextLoaderListener-	Root ApplicationContext
 * 	DispatcherServlet- WebApplicatonContext
 */
public class SpitterWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	/**
	 * 返回的带有@Configuration注解的类将会用来配置ContextLoaderListener创建的应用上下文中的bean.
	 * 
	 * 指定配置spring上下文环境的配置类---配置驱动应用后端的中间层和数据层组件bean.
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootConfig.class };
	}

	/**
	 * 带有@Configuration注解的类将会用来定义DispatcherServlet应用上下文中的bean.
	 * 
	 * 指定spring web上下文配置类---配置WebMVC相关的组件bean.
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}

	/**
	 * 将DispatcherServlet的url映射路径为"/"
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	/**
	 * 添加Filter
	 * 在这里没有必要声明它的映射路径， getServletFilters()方法返回的所有Filter都会映射到DispatcherServlet上
	 */
    @Override
    protected Filter[] getServletFilters() {
    	HiddenHttpMethodFilter httpMethodFilter = new HiddenHttpMethodFilter();
    	
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        
        return new Filter[]{ httpMethodFilter, characterEncodingFilter};
    }
    
    /**
     * 配置Servlet 3.0对multipart的支持
     * 通过重载customizeRegistration()方法（它会得到一个Dynamic作为参数） 来配置multipart的具体细节
     */
	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		String location = "/tmp/spittr/uploads"; // 存放上传文件的路径
		long maxFileSize = 2*1024*1024; // 文件大小不超过2M
        long maxRequestSize = 4*1024*1024; // 整个请求数据大小不超过4M
        int fileSizeThreshold = 0; // 文件大小阈值，当数据量大于该值时，内容将被写入文件。0表示所有的文件都会写到磁盘中.
        
        registration.setMultipartConfig(
        		new MultipartConfigElement(location, maxFileSize, maxRequestSize, fileSizeThreshold));
	}	
    
    
    
    
}