package com.clonegod.fastjson.config;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@Configuration
public class MyConfiguration {

	/**
	 * Any HttpMessageConverter bean that is present in the context will be added to the list of converters. 
	 * You can also override default converters that way.
	 * 
	 * 配置使用FastJson作为默认的HttpMessageConverter
	 */
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

}