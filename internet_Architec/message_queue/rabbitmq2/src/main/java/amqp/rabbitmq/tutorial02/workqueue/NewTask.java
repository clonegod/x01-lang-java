package amqp.rabbitmq.tutorial02.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**	
 *	消息发送方-设置消息持久化：
 *	1. 设置队列的durable=true，保存队列---防止Broker崩溃导致队列丢失
 *	2. MessageProperties.PERSISTENT_TEXT_PLAIN ---防止Broker崩溃时队列中的消息丢失
 *	3. 以上2点还不能完成保证消息不丢失---If you need a stronger guarantee then you can use "publisher confirms".
 *		Marking messages as persistent doesn't fully guarantee that a message won't be lost. 
 */
public class NewTask {

  private static final String TASK_QUEUE_NAME = "task_queue";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // make sure that RabbitMQ will never lose our queue.
    boolean durable = true;
    channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);

    if(argv.length == 0) {
    	argv = new String[]{"hellWorld..", "hellAMQP..."};
    }
    String message = getMessage(argv);
    
    // mark our messages as persistent - by supplying a delivery_mode property with a value 2.
    channel.basicPublish("", TASK_QUEUE_NAME,
        MessageProperties.PERSISTENT_TEXT_PLAIN,
        message.getBytes("UTF-8"));
    System.out.println(" [x] Sent '" + message + "'");

    channel.close();
    connection.close();
  }

  private static String getMessage(String[] strings) {
    if (strings.length < 1)
      return "Hello World!";
    return joinStrings(strings, " ");
  }

  /**
   * 将字符串数中的元素合并为一个字符串
   */
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
