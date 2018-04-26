package rabbitmq.sample05.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;


/**
 *  Direct	路由键精确匹配
 *  Fanout	广播消息
 *  Topic	路由键按模式匹配
 *  		 bindings="#"时，topic交换器的表现与fanout类似，客户端将收到所有消息，而与route key 无关。
 *  		 bindings中不含"*","#"时，topic交换器的表现与direct类似，消息严格按照route key进行投递。
 *
 */

public class EmitLogTopic {
	private static final String EXCHANGE_NAME = "topic_logs";

	public static void main(String[] argv) {
		Connection connection = null;
		Channel channel = null;
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
			
			String routingKey = getRouting(argv);
			String message = getMessage(argv);
			
			// 发布消息到topic交换器
			channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
			
			System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception ignore) {
				}
			}
		}
	}

	private static String getRouting(String[] strings) {
		if (strings.length < 1)
			return "log.info";
		return strings[0];
	}

	private static String getMessage(String[] strings) {
		if (strings.length < 2)
			return "A critical kernel error";
		return joinStrings(strings, " ", 1);
	}

	private static String joinStrings(String[] strings, String delimiter, int startIndex) {
		int length = strings.length;
		if (length == 0)
			return "";
		if (length < startIndex)
			return "";
		StringBuilder words = new StringBuilder(strings[startIndex]);
		for (int i = startIndex + 1; i < length; i++) {
			words.append(delimiter).append(strings[i]);
		}
		return words.toString();
	}
}