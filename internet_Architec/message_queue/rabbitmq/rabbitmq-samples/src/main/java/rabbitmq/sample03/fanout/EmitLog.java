package rabbitmq.sample03.fanout;
import java.util.concurrent.ThreadLocalRandom;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

/**
 * Publish/Subscribe - deliver the same message to many consumers.
 *	消息广播
 *	
 *
 * Exchanger
 * 	On one side it receives messages from producers and the other side it pushes them to queues.
 * 	1、接收生产者发送的消息
 *  2、根据routeKey路由键将消息存储到匹配的队列中
 *  
 * 交换器的职责
 *  交换器必须处理接收到的消息：
 *  	存储到指定队列？
 *  	存储到多个队列？
 *  	丢弃消息？
 *	
 * ExchangeType 交换器类型
 * 	direct
 * 	topic
 * 	fanout
 */
public class EmitLog {

  private static final String EXCHANGE_NAME = "logs";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 声明交换器，类型为FANOUT，名称为logs
    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

    for(int i = 0; i < Integer.MAX_VALUE; i++) {
    	String message = getMessage(argv);
    	
    	// 发布消息到交换器时，必须指定 routingKey，但对于fanout exchanges会忽略此参数 --- 必须传，但不用
    	channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
    	System.out.println(" [x] Sent '" + message + "'");
    	Thread.sleep(1000);
    }

    channel.close();
    connection.close();
  }

  private static String getMessage(String[] strings){
    if (strings.length < 1)
    	    return "info: Hello World!~" + ThreadLocalRandom.current().nextInt();
    return joinStrings(strings, " ");
  }

  private static String joinStrings(String[] strings, String delimiter) {
    int length = strings.length;
    if (length == 0) return "";
    StringBuilder words = new StringBuilder(strings[0]);
    for (int i = 1; i < length; i++) {
        words.append(delimiter).append(strings[i]);
    }
    return words.toString();
  }
}