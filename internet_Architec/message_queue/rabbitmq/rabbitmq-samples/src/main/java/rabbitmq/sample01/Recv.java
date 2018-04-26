package rabbitmq.sample01;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Recv {
/**
 * RabbitMQ是智能代理+哑终端。
 * 	消费端只需要指定要消费的队列即可，等待broker推送消息后处理消息。
 * 
 * - kafka是哑代理+智能终端。
 */
  private final static String QUEUE_NAME = "hello";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 1、声明队列-从哪个队列上消费数据
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    // 2、启用消息自动确认，配置消息处理器
    channel.basicConsume(QUEUE_NAME, true, 
    		new DefaultConsumer(channel) {
		        @Override
		        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
		            throws IOException {
		          String message = new String(body, "UTF-8");
		          System.out.println(" [x] Received '" + message + "'");
		          try {
		  			Thread.sleep(3000);
		  		} catch (InterruptedException e) {
		  			e.printStackTrace();
		  		}
	          
	        }
      });
  }
}