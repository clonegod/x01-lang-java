package amqp.rabbitmq.tutorial02.workqueue;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 客户端：
 * 	1. channel.basicQos(1); 设置客户端每次只处理1条消息---防止耗时长的任务总是被分配到某个客户端
 *  2. boolean autoAck = false; 关闭自动应答，在客户端处理完成当前任务后，手动向Broker发出确认信号---防止未成功处理的消息发生丢失
 *  3. 客户端每次仅接受1条消息，这些消息都是非常好使的，那就可能会导致队列被填满。解决办法： 
 *  If all the workers are busy, your queue can fill up. 
 *  You will want to keep an eye on that, and maybe 'add more workers', or 'use message TTL'.
 *
 */
public class Worker {

  private static final String TASK_QUEUE_NAME = "task_queue";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    final Connection connection = factory.newConnection();
    final Channel channel = connection.createChannel();

    channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    // tells RabbitMQ not to give more than one message to a worker at a time
    channel.basicQos(1);

    final Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");

        System.out.println(System.currentTimeMillis() + " [x] Received '" + message + "'");
        try {
          doWork(message);
        } finally {
        	// 客户端处理完消息后，再手动向Broker发出消息应答，此时Broker就可以安全的删除此条消息了
          System.out.println(" [x] Done");
          channel.basicAck(envelope.getDeliveryTag(), false);
        }
      }
    };
    
    boolean autoAck = false; // 关闭自动应答
    channel.basicConsume(TASK_QUEUE_NAME, autoAck, consumer);
  }

  /**
   * 模拟每个消息处理耗费一定时间
   */
  private static void doWork(String task) {
    for (char ch : task.toCharArray()) {
      if (ch == '.') {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException _ignored) {
          Thread.currentThread().interrupt();
        }
      }
    }
  }
}

