package clonegod.netty03.codec.marshalling;

import java.io.File;
import java.io.FileOutputStream;

import clonegod.utils.GzipUtils;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter{

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		RPCRequet req = (RPCRequet)msg;
		System.out.println("Server : " + req.toString());
		byte[] attachment = GzipUtils.ungzip(req.getAttachment());
		
		String path = System.getProperty("user.dir") + File.separatorChar + "upload" +  File.separatorChar + "jboss_upload.PNG";
        FileOutputStream fos = new FileOutputStream(path);
        fos.write(attachment);
        fos.close();
		
		RPCResponse resp = new RPCResponse();
		resp.setId(req.getId());
		resp.setContent("响应内容" + req.getId());
		ctx.writeAndFlush(resp);//.addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

	
	
}
