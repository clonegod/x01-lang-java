package clonegod.netty01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
	
	private int port;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	
	public NettyServer(int port) {
		this.port = port;
		//1 创建线两个程组 
		bossGroup = new NioEventLoopGroup(); //第一个线程组 处理服务器端接收客户端连接的
		workerGroup = new NioEventLoopGroup(); //2 第二个线程组 进行网络通信的（网络读写的）
	}
	
	public void start() {
		try {
			//3 创建一个辅助类Bootstrap，就是对我们的Server进行一系列的配置
			ServerBootstrap b = new ServerBootstrap(); 
			//设置工作线程组
			b.group(bossGroup, workerGroup)
			// 该参数只影响accept，对server支持的并发连接数没有影响
			.option(ChannelOption.SO_BACKLOG, 1024)		//设置tcp缓冲区
			.option(ChannelOption.SO_SNDBUF, 32*1024)	//设置发送缓冲大小
			.option(ChannelOption.SO_RCVBUF, 32*1024)	//这是接收缓冲大小
			.option(ChannelOption.SO_KEEPALIVE, true)	//保持连接
			.channel(NioServerSocketChannel.class) //指定NIO的模式
			//一定要使用 childHandler 去绑定具体的 事件处理器
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel sc) throws Exception {
					sc.pipeline().addLast(new ServerHandler());
				}
			});
			
			//绑定指定的端口 进行监听
			ChannelFuture f = b.bind(port).sync();
			System.out.println("Server start");
			
			//阻塞服务端线程-异步执行，避免主线程立即退出
			f.channel().closeFuture().sync();
			
		} catch(Exception e) {
			e.printStackTrace();
		}finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
		
	}

	public static void main(String[] args) throws Exception {
		NettyServer server = new NettyServer(1234);
		server.start();
	}
	
}
