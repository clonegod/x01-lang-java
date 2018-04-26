package clonegod.jms.activemq.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

public class SpringJmsSender {
	
	public static void main(String[] args) throws InterruptedException {
		ClassPathXmlApplicationContext context = 
				new ClassPathXmlApplicationContext("classpath:META-INF/spring/activemq-producer.xml");
		
		JmsTemplate template = context.getBean("jmsTemplate", JmsTemplate.class);
		int i = 0;
		while(true) {
			i = i + 1;
			template.convertAndSend("message send by SpringJmsTemplate, msg-" + i);
			Thread.sleep(3000);
			if(i > 100) {
				break;
			}
		}
		
		context.close();
	}
	
}
