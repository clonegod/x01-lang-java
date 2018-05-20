package socket03.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AioServer> {

	@Override
	public void failed(Throwable exc, AioServer attachment) {
		exc.printStackTrace();
	}
	
	@Override
	public void completed(AsynchronousSocketChannel asc, AioServer attachment) {
		// 接受新客户端的请求，并绑定对应的CompletionHandler处理通道上的数据
		AsynchronousServerSocketChannel asyncServerScoketChannel = attachment.asyncServerScoketChannel;
		asyncServerScoketChannel.accept(attachment, this);
		
		// 读取已准备好的数据
		read(asc);
		
	}

	/**
	 * 读取客户端发起的请求内容
	 * @param asc
	 */
	private void read(final AsynchronousSocketChannel asc) {
		//读取数据
		final ByteBuffer buf = ByteBuffer.allocate(1024);
		asc.read(buf, null, new CompletionHandler<Integer, Void>() {
			@Override
			public void completed(Integer dataLength, Void attachment) {
				//进行读取之后,重置标识位
				buf.flip();
				//获得读取的字节数
				System.out.println("Server -> " + "收到客户端的数据长度为:" + dataLength);
				//获取读取的数据
				String resultData = new String(buf.array()).trim();
				System.out.println("Server -> " + "收到客户端的数据信息为:" + resultData);
				String response = "服务器响应, 收到了客户端发来的数据: " + resultData;
				write(asc, response);
			}
			@Override
			public void failed(Throwable exc, Void attachment) {
				exc.printStackTrace();
			}
		});
	}

	/**
	 * 响应客户端
	 * @param asc
	 * @param response
	 */
	private void write(AsynchronousSocketChannel asc, String response) {
		ByteBuffer buf = ByteBuffer.allocate(1024);
		buf.put(response.getBytes());
		buf.flip();
		// 异步写-向客户端返回数据
		asc.write(buf, null, new CompletionHandler<Integer, Void>() {
			// 异步写成功
			@Override
			public void completed(Integer result, Void attachment) {
				System.out.println("向客户端异步写数据完成。");
			}
			// 异步写失败
			@Override
			public void failed(Throwable exc, Void attachment) {
				exc.printStackTrace();
			}
		});
	}
	

}
