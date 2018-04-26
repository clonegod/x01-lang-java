package rabbitmq.sample02.queue;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * 【工作队列-消息堆积 + 集群消费模式】
 * The assumption behind a work queue is that each task is delivered to exactly one worker.
 * Work Queues (aka: Task Queues) 
 * 工作队列（又名：任务队列）背后的主要思想是避免立即执行资源密集型任务，必须等待前面的任务完成，才会安排稍后的任务。
 *  我们把一个任务封装成一个消息并发送给一个队列。
 *  在后台运行的工作进程将任务取出并最终执行作业。 
 *  
 *  当你运行许多worker消费者时时，任务将在他们之间共享（让每个消费者处理的任务数基本均衡）。
 *	多个worker消费者一起处理同一个队列上的任务，每个任务仅被其中一个消费者处理
 * 	每个消息仅投递给1次，即只被某个客户端消费1次
 * 
 * Round-robin dispatching	按顺序轮询方式投递消息给消费端。
 * we can just add more workers and that way, scale easily.	可随时增加worker节点，水平扩展集群的消费能力
 * 
 *  
 * 【消息确认机制-防止消息丢失】 
 * message acknowledgments 消息确认-客户端消费消息成功后，反馈给broker，此时broker才能将该消息标记为删除
 * 	Manual message acknowledgments are turned on by default. 默认使用手动确认
 *  send a proper acknowledgment from the worker, once we're done with a task. 当worker处理任务完成后，才向broker发出确认
 *  
 *  Forgotten acknowledgment
 *  Messages will be redelivered when your client quits (which may look like random redelivery), 忘记确认消息，那将导致在客户端退出时，它所处理的消息被转发给其它consumer。 
 *  but RabbitMQ will eat more and more memory as it won't be able to release any unacked messages.同时也会造成broker内存耗费越来越多，因为未确认消息不能删除。
 *  
 * 【consumer不可达时，那些未确认的消息如何处理?】
 * 	1、消费者所在server宕机；
 *  2、channel通道关闭，connection连接关闭，tcp连接丢失，网络故障等
 *  只有broker发现consumer宕机时，才会将该consumer未确认的消息，转给其它consumer处理，消息确认不存在超时，客户端可以处理很长时间后再进行确认。
 *  当broker发现consumer失联后，就会将那些已投递到该consumer处理但尚未确认的消息，转投到其它的consumer去处理
 *  
 *  【消息持久性-防止broker崩溃导致丢失消息】
 *  1、声明队列持久化
 *  2、发布消息时，设置消息持久化	MessageProperties.PERSISTENT_TEXT_PLAIN
 *  3、更严格的消息持久化保证-publisher confirms.
 */
public class NewTask {

  private static final String TASK_QUEUE_NAME = "task_queue";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 声明队列，设置队列需要持久化
    channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

    for(int i = 0; i < 100; i++) {
    	String message = getMessage(argv) + "-" + i;
    	// 发布消息到默认交换器，消息需要持久化
    	channel.basicPublish("", TASK_QUEUE_NAME,
    			MessageProperties.PERSISTENT_TEXT_PLAIN,
    			message.getBytes("UTF-8"));
    	System.out.println(" [x] Sent '" + message + "'");
    }

    channel.close();
    connection.close();
  }

  private static String getMessage(String[] strings) {
    if (strings.length < 1)
      return ".Hello World!";
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