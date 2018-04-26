package spittr.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 对Controller定义的Aspect需要在WebApplication中进行配置
 *
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true) //使用javaconfig配置EnableAspectJAutoProxy时，需要将Aspect声明为@Component
public class AspectConfig {
	// no explicit @Bean definitions required
}
