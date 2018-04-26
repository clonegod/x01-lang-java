package rabbitmq.sample03.fanout;
import com.rabbitmq.client.*;

import java.io.IOException;

public class ReceiveLogs {
  private static final String EXCHANGE_NAME = "logs";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 1、声明类型为FANOUT的交换器，命名为logs
    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
    
    // Temporary queues
    // 2、创建1个临时队列：non-durable, exclusive, autodelete queue， auto generated name
    String queueName = channel.queueDeclare().getQueue();
    System.out.println("Generated queue name = " + queueName);
    
    // 3、将队列绑定到交换器上 Binding: tell the exchange to send messages to our queue by routeKey - 注：fanout类型的Exchange不需要routeKey.
    channel.queueBind(queueName, EXCHANGE_NAME, "routingKey");

    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope,
                                 AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + message + "'");
      }
    };
    
    // 4、开始消费
    channel.basicConsume(queueName, true, consumer);
  }
}