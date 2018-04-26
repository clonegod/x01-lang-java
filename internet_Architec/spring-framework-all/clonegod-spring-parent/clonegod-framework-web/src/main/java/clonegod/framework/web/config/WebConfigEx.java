package clonegod.framework.web.config;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import clonegod.framework.web.controllers.MonitorHelper;
import clonegod.framework.web.interceptor.MonitorInterceptor;

@Configuration
@EnableWebMvc
public class WebConfigEx extends WebMvcConfigurerAdapter {

    @Autowired
    private MonitorHelper monitorHelper;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        stringHttpMessageConverter.setSupportedMediaTypes(
                Arrays.asList(new MediaType("text", "plain", Charset.forName("UTF-8"))));
        converters.add(stringHttpMessageConverter);
        
        /** 启用JSON转换器以支持@ResponseBody --- No converter found for return value of type ... */
        converters.add(new MappingJackson2HttpMessageConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        MonitorInterceptor monitorInterceptor = new MonitorInterceptor();
        monitorInterceptor.setMonitorHelper(monitorHelper);
        registry.addInterceptor(monitorInterceptor)
                .addPathPatterns("/**");
    }

}
