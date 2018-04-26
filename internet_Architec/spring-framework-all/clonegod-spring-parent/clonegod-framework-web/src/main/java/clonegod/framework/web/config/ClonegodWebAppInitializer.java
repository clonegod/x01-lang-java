package clonegod.framework.web.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import clonegod.framework.dal.config.DataSourceConfig;
import clonegod.framework.dal.config.MybatisConfig;
import clonegod.framework.web.filter.MonitorFilter;

/**
 * spring 启动时的配置入口
 *
 */
public class ClonegodWebAppInitializer implements WebApplicationInitializer {
	
    public void onStartup(ServletContext servletContext) throws ServletException {
    	// datasource & mybatis
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(DataSourceConfig.class, MybatisConfig.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));

        //servlets & contorllers --- 指定需要读取的java config 文件
//        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        AnnotationConfigWebApplicationContext webContext = rootContext;
        webContext.register(WebConfig.class, WebConfigEx.class, ServiceConfigEx.class);

        //filters
        FilterRegistration.Dynamic encodingFilterRegistration =
                servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
        encodingFilterRegistration.setInitParameter("encoding", "UTF-8");
        encodingFilterRegistration.setInitParameter("forceEncoding", "true");
        encodingFilterRegistration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST,
                DispatcherType.FORWARD), true, "/*");


        FilterRegistration.Dynamic monitorFilterRegistration =
                servletContext.addFilter("monitorFilter", new MonitorFilter());
        monitorFilterRegistration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST,
                DispatcherType.FORWARD), true, "/*");

        ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", new DispatcherServlet(webContext));
        registration.setLoadOnStartup(1);
        registration.addMapping("/");

    }
}
