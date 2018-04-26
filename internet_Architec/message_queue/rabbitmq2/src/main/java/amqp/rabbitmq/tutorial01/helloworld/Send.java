package amqp.rabbitmq.tutorial01.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

  private final static String QUEUE_NAME = "hello";
  
  /**
   * 1. 连接 RabbitMQ server.
   * 2. 确认被操作的队列已经存在. 
   * 3. 向指定的exchange发送消息
   * 
   * @param argv
   * @throws Exception
   */
  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
//    factory.setPort(5672);
//    factory.setUsername("guest");
//    factory.setPassword("guest");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    
    String message = "Hello World!" + System.currentTimeMillis();
    
    // 空字符串表示使用默认的exchange
    channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
    
    System.out.println(" [x] Sent '" + message + "'");
    
    channel.close();
    connection.close();
  }
}
