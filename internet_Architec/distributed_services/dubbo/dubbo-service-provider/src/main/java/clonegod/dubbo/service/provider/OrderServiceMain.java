package clonegod.dubbo.service.provider;

import com.alibaba.dubbo.container.Main;
import com.alibaba.dubbo.container.spring.SpringContainer;

public class OrderServiceMain  {
	
	/**
	 * 直接通过dubbo提供的启动类Main启动spring容器，发布dubbo服务
	 */
	public static void main(String[] args) {
		/**
		 * com.alibaba.dubbo.container.spring.SpringContainer
		 * 		DEFAULT_SPRING_CONFIG = "classpath*:META-INF/spring/*.xml";
		 * 		context = new ClassPathXmlApplicationContext(configPath.split("[,\\s]+"));
		 */
		// 可以指定需要发布服务所在的配置文件
		System.setProperty(SpringContainer.SPRING_CONFIG, 
				"classpath*:META-INF/spring/dubbo-service-provider-order.xml");
		
		Main.main(args);
	}
}
