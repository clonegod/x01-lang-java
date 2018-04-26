package rabbitmq.sample01;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *  
 *
 */
public class Send {

  private final static String QUEUE_NAME = "hello";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    
    // 声明队列
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    
    for(int i = 0; i < 3; i++) {
    	String message = "Hello World!";
    	
    	// 发送消息到默认的交换器-direct，消息将投递到与routeKey同名的队列上
    	String routeKey = QUEUE_NAME;
    	// The first parameter is the the name of the exchange. 
    	// The empty string denotes the default or nameless exchange: messages are routed to the queue with the name specified by routingKey, if it exists.
    	channel.basicPublish("", routeKey, null, message.getBytes("UTF-8"));
    	
    	System.out.println(" [x] Sent '" + message + "'");
    }

    channel.close();
    connection.close();
  }
}