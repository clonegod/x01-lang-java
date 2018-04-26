package amqp.rabbitmq.tutorial05.topic;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Random;

public class ReceiveLogsTopic {

  private static final String EXCHANGE_NAME = "topic_logs";
  
  static String[][] bindingKeys = new String[][] {
	  {"#"},  // receive all the logs
	  {"kern.*"},  // receive all logs from the facility "kern"
	  {"*.critical"},  // hear only about "critical" logs
	  {"kern.*", "*.critical"},  // multiple bindings
	  {"kern.error"},  // exact bindings
  };
  
  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
    String queueName = channel.queueDeclare().getQueue();
    
    if(argv.length == 0) {
    	argv = bindingKeys[new Random().nextInt(bindingKeys.length)];
    }
    if (argv.length < 1) {
      System.err.println("Usage: ReceiveLogsTopic [binding_key]...");
      System.exit(1);
    }

    for (String bindingKey : argv) {
    	System.out.println("bindingKey="+bindingKey);
      channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
    }

    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope,
                                 AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
      }
    };
    channel.basicConsume(queueName, true, consumer);
  }
}

