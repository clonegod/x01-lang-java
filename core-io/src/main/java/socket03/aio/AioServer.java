package socket03.aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AioServer {
	//线程池 - 通过该线程池为线程组提供可执行任务的线程
	private ExecutorService executorService;
	
	//线程组 - 通过一组线程来完成异步IO相关的操作（建立连接，读写数据等）
	
	private AsynchronousChannelGroup asyncChannelGroup;
	
	//异步服务器通道
	public AsynchronousServerSocketChannel asyncServerScoketChannel;
	
	private int port;
	
	public AioServer(int port){
		this.port = port;
	}
	
	public void start() {
		try {
			//创建一个缓存池 - 使用cachedThreadPool，可动态调整线程池中线程个数
			executorService = Executors.newCachedThreadPool();
			//创建线程组
			asyncChannelGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);
			//创建服务器通道
			asyncServerScoketChannel = AsynchronousServerSocketChannel.open(asyncChannelGroup);
			//进行绑定
			asyncServerScoketChannel.bind(new InetSocketAddress("127.0.0.1", port));
			
			System.out.println("server start , port : " + port);
			
			//准备接收一个客户端发起的连接请求
			asyncServerScoketChannel.accept(this, new ServerCompletionHandler());
			
			//一直阻塞 不让服务器停止（真正环境下程序是运行在Tomcat容器中的，只要Tomcat容器不退出，AIO Server线程就不会退出）
			System.out.println("Waiting New Connection...");
			Thread.sleep(Integer.MAX_VALUE);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		AioServer server = new AioServer(8765);
		server.start();
	}
	
}
