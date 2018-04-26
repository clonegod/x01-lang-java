package clonegod.netty04.runtime;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class ServerReadTimeoutHandler extends ReadTimeoutHandler {

	public ServerReadTimeoutHandler(int timeoutSeconds) {
		super(timeoutSeconds);
	}

	public ServerReadTimeoutHandler(long timeout, TimeUnit unit) {
		super(timeout, unit);
	}

	@Override
	protected void readTimedOut(ChannelHandlerContext ctx) throws Exception {
		super.readTimedOut(ctx);
		System.out.println("监测到通道超时，断开通道连接！！！channel: " + ctx.channel().toString());
	}


}
