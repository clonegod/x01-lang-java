package amqp.rabbitmq.tutorial01.helloworld;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Recv {

  private final static String QUEUE_NAME = "hello";
  /**
   * 1. 连接 RabbitMQ server.
   * 2. 确认被操作的队列已经存在. 
   * 3. 消费队列中的消息
   * 
   * @param argv
   * @throws Exception
   */
  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
          throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + message + "'");
      }
    };
    // 处理指定队列中的消息
    channel.basicConsume(QUEUE_NAME, true, consumer);
  }
}
