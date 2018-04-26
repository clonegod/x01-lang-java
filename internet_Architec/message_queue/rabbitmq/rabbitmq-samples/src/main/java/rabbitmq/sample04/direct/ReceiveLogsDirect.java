package rabbitmq.sample04.direct;

import com.rabbitmq.client.*;
import java.io.IOException;


/**
 * 交换器类型-Direct
 * Queue - 临时队列
 * Binding - info/warning/error
 *
 */
public class ReceiveLogsDirect {
	private static final String EXCHANGE_NAME = "direct_logs";

	public static void main(String[] argv) throws Exception {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
		
		String queueName = channel.queueDeclare().getQueue();
		
		/**
		 * 不同的消费者可以设置不同的routeKey，可选择性接收消息---而不是像fanout一样广播所有消息给客户端
		 */
		if (argv.length < 1) {
			System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]");
//			System.exit(1);
			argv = new String[] {"error"};
		}
		
		/** 一个队列可以设置多个bindings--即exchange可以使用不同的路由将消息转发到队列中 */
		for (String severity : argv) {
			// 绑定队列与交换器，并设置路由规则
			channel.queueBind(queueName, EXCHANGE_NAME, severity);
		}
		
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
			}
		};
		
		channel.basicConsume(queueName, true, consumer);
	}
}