package clonegod.netty01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler  extends ChannelHandlerAdapter {
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("server channel active... ");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//do something msg
		ByteBuf buf = (ByteBuf)msg;
		
		byte[] data = new byte[buf.readableBytes()];
		buf.readBytes(data);
		String request = new String(data, "utf-8");
		System.out.println("Server: " + request);
		
		//写给客户端
		String response = "echo-" + request;
		
		if(request.startsWith("bye")) {
			ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()))
			// 不设置listener，则服务端与客户端保持长连接。
			.addListener(ChannelFutureListener.CLOSE); // 异步发送完数据之后，主动断开与客户端的连接通道 
			
		} else {
			ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()))
			.addListener(ChannelFutureListener.CLOSE_ON_FAILURE); // 发生异常时，与客户端断开连接
		}
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx)
			throws Exception {
		System.out.println("读完了");
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
