package spittr.config;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import spittr.config.RootConfig.WebPackage;

/**
 * Spring应用的根上下文配置类
 * 
 */
@Configuration
@Import(value= {DataSourceConfig.class})
@ComponentScan(basePackages = { "spittr" }, 
		excludeFilters = {
				@Filter(type = FilterType.CUSTOM, value = WebPackage.class) // 自动扫描时，防止重复配置web组件
})
public class RootConfig {
	
	static final Logger logger = LoggerFactory.getLogger(RootConfig.class);
	
	public static class WebPackage extends RegexPatternTypeFilter {
		static final Pattern pattern = Pattern.compile("spittr\\.web");

		public WebPackage() {
			super(pattern);
			logger.info("Filter WebPackage pattern:{}", pattern.toString());
		}

		/*
		 * 自定义web相关组件的过滤规则，因为web相关组件的初始化是通过WebConfig来进行配置的。 
		 */
		@Override
		protected boolean match(ClassMetadata metadata) {
			boolean isMatch = false;
			// isMatch = super.match(metadata); // 根据正则表达式进行匹配过滤
			
			isMatch = metadata.getClassName().matches("(?i)(.*web.*|.*ViewResolver.*|.*Controller.*)"); 
			logger.info("class={}, filter={}", metadata.getClassName(), isMatch);
			
			return isMatch;
		}
	}
	
}
