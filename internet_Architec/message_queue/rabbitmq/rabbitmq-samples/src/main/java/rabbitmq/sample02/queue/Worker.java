package rabbitmq.sample02.queue;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class Worker {

  private static final String TASK_QUEUE_NAME = "task_queue";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    final Connection connection = factory.newConnection();
    final Channel channel = connection.createChannel();

    channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    // prefetchCount - 限制1次仅推送1条消息，直到消息被消费端确认消费成功。否则，不会推送下一条消息。
//    channel.basicQos(1);

    final Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        String now = new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date());
        System.out.println(" [x] Received '" + message + "'" + "---"+now);
        try {
          doWork(message);
        } finally {
          System.out.println(" [x] Done");
          if(ThreadLocalRandom.current().nextBoolean() == true) {
        	  System.err.println("Consumer crashed...消息未能成功消费，此消息将被broker分配给其它正常的consumer处理");
        	  System.exit(1);
          } else {
        	  // 消息处理完成后，向broker确认此条消息已处理完毕，broker可以将其删除了
        	  channel.basicAck(envelope.getDeliveryTag(), false);
          }
        }
      }
    };
    
    // 关闭自动确认，由消费端处理完消息后主动向broker确认。
    channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
  }

  private static void doWork(String task) {
    for (char ch : task.toCharArray()) {
      if (ch == '.') {
        try {
          Thread.sleep(3000);
        } catch (InterruptedException _ignored) {
          Thread.currentThread().interrupt();
        }
      }
    }
  }
}