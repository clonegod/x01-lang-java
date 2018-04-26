package clonegod.netty03.codec.marshalling;

import java.io.File;
import java.io.FileInputStream;

import clonegod.utils.GzipUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

	
	public static void main(String[] args) throws Exception{
		
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group)
		 .channel(NioSocketChannel.class)
		 .handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder()); // 设置解码器（解码服务端返回的数据为POJO）
				sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder()); // 设置编码器（将发送到服务器的数据进行编码）
				sc.pipeline().addLast(new ClientHandler());
			}
		});
		
		ChannelFuture cf = b.connect("127.0.0.1", 8765).sync();
		
		for(int i = 0; i < 5; i++ ){
			RPCRequet req = new RPCRequet();
			req.setId("" + i);
			req.setContent("上传一张图片到服务器" + i);	
			String path = System.getProperty("user.dir") + File.separatorChar + "upload" +  File.separatorChar + "jboss.PNG";
			File file = new File(path);
	        FileInputStream in = new FileInputStream(file);  
	        byte[] data = new byte[in.available()];  
	        in.read(data);  
	        in.close(); 
			req.setAttachment(GzipUtils.gzip(data));
			cf.channel().writeAndFlush(req);
		}

		cf.channel().closeFuture().sync();
		group.shutdownGracefully();
	}
}
