package clonegod.springcloud.stream.kafka.stream.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import clonegod.springcloud.stream.kafka.stream.messaging.MySource;

/**
 * spring cloud stream 消息生产者 Bean
 *	消息驱动Bean
 */
@Component
@EnableBinding({Source.class, MySource.class}) // 绑定一个消息管道（Source会注册一个与它关联的Channel到容器中）
public class MessageProducerBean {

	@Autowired
	@Qualifier(Source.OUTPUT) // 限定Bean的名称（如果有多个的话，以示区分）
	private MessageChannel messageChannel;
	
	
	
	@Autowired
	@Qualifier(MySource.MyOUTPUT) // 限定Bean的名称（如果有多个的话，以示区分）
	private MessageChannel myMessageChannel;
	
	
	/**
	 * 发送消息
	 */
	public boolean send(String message) {
		// 通过消息管道发送消息
		boolean sendResult = messageChannel.send(MessageBuilder.withPayload(message).build());
		return sendResult;
	}
	
	/**
	 * 发送消息
	 */
	public boolean sendByMySource(String message) {
		// 通过消息管道发送消息
		boolean sendResult = myMessageChannel.send(MessageBuilder.withPayload(message).build());
		return sendResult;
	}
	
}
