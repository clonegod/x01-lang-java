package clonegod.netty02.pipeline;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter {


	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(" server channel active... ");
	}

	/**
	 * SocketChannel 在数据处理管道上添加了StringDecoder，所以这里的Object msg参数实际已经被转换为了字符串类型
	 * 	sc.pipeline().addLast(new StringDecoder());
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String request = (String)msg;
		System.out.println("Server :" + request);
		String response = "服务器响应：" + request + "$_";
		
		// 向客户端返回数据时，仍然需要使用ByteBuffer来封装数据，不能直接发送字符串。
		ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable t) throws Exception {
		ctx.close();
	}




}
