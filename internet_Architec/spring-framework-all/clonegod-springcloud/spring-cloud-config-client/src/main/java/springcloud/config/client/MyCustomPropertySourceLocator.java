package springcloud.config.client;

import java.util.Collections;

import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

/**
 * 自定义PropertySource
 *	在META-INF/spring.factories中，加入SPI接口定义，即可在系统启动时加入该类，并将该类定义的property设置到应用程序上下文中。
 *	属性最终是否能失效，还要看是否有其它PropertySource对其进行了覆盖，或者是被优先级高的覆盖了。
 *
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MyCustomPropertySourceLocator implements PropertySourceLocator {

    @Override
    public PropertySource<?> locate(Environment environment) {
        return new MapPropertySource("customProperty",
                Collections.<String, Object>singletonMap("server.port", 9000));
    }

}