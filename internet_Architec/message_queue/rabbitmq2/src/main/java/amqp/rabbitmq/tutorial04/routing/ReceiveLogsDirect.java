package amqp.rabbitmq.tutorial04.routing;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Arrays;

public class ReceiveLogsDirect {

  private static final String EXCHANGE_NAME = "direct_logs";

  /**
   * 1. ExchangeType	DIRECT
   * 2. Queue 			Temporal Queue
   * 
   * @param argv
   * @throws Exception
   */
  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
    String queueName = channel.queueDeclare().getQueue();
    
    if(argv.length == 0) {
    	argv = Math.random() > 0.5 ? new String[] {"error"} : new String[] {"info", "warning"};
    	System.out.println("routekey="+Arrays.toString(argv));
    }
    
    if (argv.length < 1){
      System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]");
      System.exit(1);
    }

    for(String severity : argv){
      channel.queueBind(queueName, EXCHANGE_NAME, severity);
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

