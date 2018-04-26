package rabbitmq.sample06.rpc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;


public class RPCClient {
	private static Connection connection;
	private static String requestQueueName = "rpc_queue";
	private static String replyQueueName;
	
	private static final Map<String,String> corrIdMap = new HashMap<>();
	final BlockingQueue<String> response;
	
	public RPCClient() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		// 初始化连接
		connection = factory.newConnection();
		
		// creates an anonymous exclusive callback queue.
		// rpc请求的回调队列。每个客户端仅创建1个临时队列用来接收服务端的响应结果，同一个客户端的不同响应共享此队列。
		replyQueueName = connection.createChannel().queueDeclare().getQueue();
		response = new ArrayBlockingQueue<String>(1);
	}
	
	public String call(String message) throws IOException, InterruptedException {
		// 标识响应结果属于哪个请求---每个请求都有一个唯一的id标识
		AMQP.BasicProperties props = new AMQP.BasicProperties
										.Builder()
										.correlationId(corrIdMap.get("corrId"))
										.replyTo(replyQueueName)
										.build();
		
		Channel channel = connection.createChannel();
		// 发送请求到请求队列
		channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));
		
		// 阻塞等待从响应队列获取结果
		channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				System.out.println(Thread.currentThread().getName() + "\t" 
					+ String.format("corrId=%s, resCorrId=%s", corrIdMap.get("corrId"), properties.getCorrelationId()));
				
				if (properties.getCorrelationId().equals(corrIdMap.get("corrId"))) {
					response.offer(new String(body, "UTF-8"));
				} else {
					System.err.println("corrId not equals");
					System.exit(1);
				}
			}
		});
		// 阻塞等待异步线程池返回结果
		return response.take();
	}

	public void close() throws IOException {
		connection.close();
	}

	public static void main(String[] argv) throws Exception {
		final RPCClient fibonacciRpc = new RPCClient();
		
		// 这里仍然是阻塞这一个一个发送的request，如何实现并发request，又能保证corrId关联的正确呢？
		for(int i = 0; i < 5; i++) {
			corrIdMap.put("corrId", UUID.randomUUID().toString());
			System.out.println(String.format(" [x] Requesting fib(%s)", i));
			String response = fibonacciRpc.call(String.valueOf(i));
			System.out.println(" [.] Got '" + response + "'");
		}
		
		System.in.read();
		
		fibonacciRpc.close();
	}


}