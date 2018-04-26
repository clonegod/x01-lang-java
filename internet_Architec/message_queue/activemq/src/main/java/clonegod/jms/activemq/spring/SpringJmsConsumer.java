package clonegod.jms.activemq.spring;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringJmsConsumer {
	public static void main(String[] args) throws IOException {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:META-INF/spring/activemq-consumer.xml");
		
		System.in.read();
		
		context.close();

	}
}
