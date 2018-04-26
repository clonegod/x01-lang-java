package amqp.rabbitmq.tutorial03.fanout;

import com.rabbitmq.client.*;

import java.io.IOException;

public class ReceiveLogs {
  private static final String EXCHANGE_NAME = "logs";

  /**
   * 客户端：
   * 1. Exchanges---使用FANOUT模型
   * 2. Temporary queues---对于瞬时态消息的处理，比如日志信息只考虑当前日志，适合使用临时队列
   * 3. 绑定临时队列到Exchange---FANOUT模型设置routingKey为空字符串
   * 
   * @param argv
   * @throws Exception
   */
  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
    // whenever we connect to Rabbit we need a fresh, empty queue.
    String queueName = channel.queueDeclare().getQueue(); // return a random queue name
    channel.queueBind(queueName, EXCHANGE_NAME, "");

    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope,
                                 AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + message + "'");
      }
    };
    channel.basicConsume(queueName, true, consumer);
  }
}

