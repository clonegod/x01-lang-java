package clonegod.springcloud.stream.kafka.stream.consumer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

/**
 * 消息消费Bean
 *
 */
@Component
@EnableBinding({Sink.class})
public class MessageConsumerBean {

	
	@Autowired
	@Qualifier(Sink.INPUT) // 限定Bean的名称（如果有多个的话，以示区分）
	private SubscribableChannel subscribableChannel;
	
	
	@Autowired
	private Sink sink; // sink.input() == subscribableChannel
	
	
	@PostConstruct // 依赖注入完成后，进行的初始化操作
	public void init() {
		// 方式一
		subscribableChannel.subscribe(new MessageHandler() {
			@Override
			public void handleMessage(Message<?> message) throws MessagingException {
				System.out.println("subscribe: "+message.getPayload());
			}
		});
		
		// 方式二
		sink.input().subscribe(msg -> {
			System.err.println(msg);
		});
	}
	
	// 方式三
	@ServiceActivator(inputChannel=Sink.INPUT)
	public void onMessage(Object message) {
		System.out.println("@ServiceActivator: " + message);
	}
	
	// 方式四
	@StreamListener(Sink.INPUT)
	public void onMessage(String message) {
		System.out.println("@StreamListener: " + message);
	}
	
	
}
