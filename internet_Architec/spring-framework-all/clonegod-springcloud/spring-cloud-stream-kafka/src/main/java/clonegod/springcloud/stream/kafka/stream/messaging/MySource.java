package clonegod.springcloud.stream.kafka.stream.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 自定义的消息Source
 *
 */
public interface MySource {
	
	String MyOUTPUT = "myoutput";

	@Output(MySource.MyOUTPUT)
	MessageChannel output();
}
