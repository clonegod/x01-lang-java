package clonegod.netty01;

import java.util.Scanner;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

	public static void main(String[] args) throws Exception {
		EventLoopGroup workgroup = null;
		try {
			workgroup = new NioEventLoopGroup();
			Bootstrap b = new Bootstrap();
			b.group(workgroup)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel sc) throws Exception {
					sc.pipeline().addLast(new ClientHandler());
				}
			});
			
			ChannelFuture cf = b.connect("127.0.0.1", 1234).sync();
			
			Scanner sc = new Scanner(System.in);
			String line = null;
			while((line = sc.nextLine()) != null) {
				//buf
				ByteBuf data = Unpooled.copiedBuffer(line.getBytes("utf-8"));
				cf.channel().writeAndFlush(data);
				if(line.startsWith("bye")) {
					System.out.println("客户端向服务端发起断开连接请求");
					// cf.channel().close(); // 断开channel通道需要在服务端完成，因此客户端向服务端发一个标识，由服务器端来断开连接。
					break;
				}
			}
			sc.close();
			
			// 阻塞客户端线程
			cf.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workgroup.shutdownGracefully();
		}
		
		
	}
}
