package clonegod.netty04.runtime;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class Client {
	/**
	 * 私有静态内部类-单例模式 
	 */
	private static class SingletonHolder {
		static final Client instance = new Client();
	}
	
	public static Client getInstance(){
		return SingletonHolder.instance;
	}
	
	private EventLoopGroup group;
	private Bootstrap b;
	private ChannelFuture cf ;
	
	private Client(){
			group = new NioEventLoopGroup();
			b = new Bootstrap();
			b.group(group)
			 .channel(NioSocketChannel.class)
			 .handler(new LoggingHandler(LogLevel.INFO))
			 .handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel sc) throws Exception {
						sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
						sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
						//超时handler（当服务器端与客户端在指定时间内没有进行任何通信，则会关闭相应的通道，主要为减小服务端资源占用）
						sc.pipeline().addLast(new ReadTimeoutHandler(5)); 
						sc.pipeline().addLast(new ClientHandler());
					}
		    });
	}
	
	public void connect(){
		try {
			this.cf = b.connect("127.0.0.1", 8765).sync();
			System.out.println("远程服务器已经连接, 可以进行数据交换... cf:" + cf.channel().toString());				
		} catch (Exception e) {
			throw new RuntimeException("与服务器建立连接失败", e);
		}
	}
	
	public ChannelFuture getChannelFuture(){
		// 通道未连接，则建立新的连接
		if(this.cf == null){
			this.connect();
		}
		// 通道已关闭，则建立新的连接
		if(! this.cf.channel().isActive()){
			System.out.println("【x】正在建立新的连接通道");
			this.connect();
		}
		
		return this.cf;
	}
	
	public static void main(String[] args) throws Exception{
		
		final AtomicInteger generator = new AtomicInteger(0);
		final BlockingQueue<Request> dataFlow = new LinkedBlockingQueue<>();
		
		// 定时收集数据的线程
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					Request request = new Request();
					int seqNo = generator.incrementAndGet();
					request.setId("" + seqNo);
					request.setName("pro" + seqNo);
					request.setRequestMessage("数据信息" + seqNo);
					try {
						dataFlow.put(request);
						Thread.sleep(seqNo * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		final Client c = Client.getInstance();
		
		// 定时上报数据的线程
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@Override
			public void run() {
				// 客户端线程永不退出
				while(true) {
					System.out.println("=====> 客户端准备向服务器发送数据");
					ChannelFuture cf = null;
					while(true) {
						try {
							Request request = dataFlow.poll(3, TimeUnit.SECONDS);
							if(request == null) {
								System.out.println("=====> 客户端没有数据可发送，break");
								break;
							}
							System.out.println("=====> 客户端已有数据，准备发送数据");
							// 有数据了，获取/新建与服务器的连接
							cf = c.getChannelFuture();
							cf.channel().writeAndFlush(request);
							TimeUnit.SECONDS.sleep(4);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					try {
						if(cf != null) {
							System.out.println("=====> 客户端阻塞等待服务器断开连接");
							cf.channel().closeFuture().sync();
							System.out.println("=====> 通道已超时，被服务器断开连接");
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
	}
	
}
