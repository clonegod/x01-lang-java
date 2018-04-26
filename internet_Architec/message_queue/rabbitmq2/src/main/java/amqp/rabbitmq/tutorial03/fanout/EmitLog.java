package amqp.rabbitmq.tutorial03.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class EmitLog {

  private static final String EXCHANGE_NAME = "logs";
  
  /**
   * 1. Exchange使用fanout模型，实现消息广播功能。
   * 2. 发送方只面向Exchange发送消息，不需要考虑queue的存在与否。
   * 
   * @param argv
   * @throws Exception
   */
  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    
    // 1. 指定具体的Exchange类型为FANOUT
    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

    if(argv.length == 0) {
    	argv = new String[]{"INFO: Exchange mode type = FANOUT..."};
    }
    String message = getMessage(argv);
    
    // 2. 由于Exchange的类型为FANOUT，因此routingKey设置为空字符串即可。
    channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
    System.out.println(" [x] Sent '" + message + "'");

    channel.close();
    connection.close();
  }

  private static String getMessage(String[] strings){
    if (strings.length < 1)
    	    return "info: Hello World!";
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

