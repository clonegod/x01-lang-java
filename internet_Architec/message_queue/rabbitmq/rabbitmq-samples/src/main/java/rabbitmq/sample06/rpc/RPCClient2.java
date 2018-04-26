package rabbitmq.sample06.rpc;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * Client 提交请求到“请求队列”
 * Server 从“请求队列”取出消息，计算处理，将结果写入到“结果队列”
 * Client 从“结果队列”取结果
 *
 */
public class RPCClient2 {
	private static Connection connection; // 每个客户端仅持有1个与broker的连接
	private static final String requestQueueName = "rpc_queue"; // 客户端发布消息的queue
	private static String replyQueueName; // 生产端返回结果的队列
	
	private static final BlockingQueue<String> response = new LinkedBlockingQueue<>(); // 存储响应结果的阻塞队列容器
	
	public RPCClient2() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		// 初始化连接
		connection = factory.newConnection();
		
		// creates an anonymous exclusive callback queue.
		// rpc请求的回调队列。每个客户端仅创建1个临时队列用来接收服务端的响应结果，同一个客户端的不同响应共享此队列。
		replyQueueName = connection.createChannel().queueDeclare().getQueue();
	}
	
	public void close() throws IOException {
		connection.close();
	}

	public static void main(String[] argv) throws Exception {
		final RPCClient2 fibonacciRpc = new RPCClient2();
		ThreadPoolExecutor executor = new ThreadPoolExecutor(
											5, 
											10, 
											60, 
											TimeUnit.SECONDS, 
											new LinkedBlockingQueue<Runnable>(100));
		executor.prestartAllCoreThreads();
		
		/** 模拟客户端发起多个request请求，每个请求用1个线程来执行  */
		for(int i = 0; i < 10; i++) {
			executor.execute(new ReqTask(String.valueOf(i)));
		}

		for(int i = 0; i < 10; i++) {
			System.out.println(response.take());
		}
		
		executor.awaitTermination(1, TimeUnit.SECONDS);
		executor.shutdownNow();
		
		fibonacciRpc.close();
	}

	
	
	static class ReqTask implements Runnable {
		private String message;
		
		public ReqTask(String message) {
			this.message = message;
		}

		@Override
		public void run() {
			try {
				String corrId = UUID.randomUUID().toString().replace("-", "");
				System.out.println(Thread.currentThread().getName() + "\t" + String.format(" [x] Requesting fib(%s), corrId=%s", message, corrId));
				
				// channel不能在线程间共享，需要为每个线程创建一个新的channel
				Channel channel = connection.createChannel();
				
				// 标识响应结果属于哪个请求---每个请求都有一个唯一的id标识
				AMQP.BasicProperties props = new AMQP.BasicProperties
												.Builder()
												.correlationId(corrId)
												.replyTo(replyQueueName)
												.build();
				
				// 发送请求到请求队列-生产者
				channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));
				
				// 消费结果队列-消费者
				channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
					@Override
					public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
							byte[] body) throws IOException {
						try {
							String corrId = properties.getCorrelationId();
							String result = new String(body, "UTF-8");
							String res = String.format("RES-corrId=%s, result=%s", corrId, result);
							response.put(res);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	

}