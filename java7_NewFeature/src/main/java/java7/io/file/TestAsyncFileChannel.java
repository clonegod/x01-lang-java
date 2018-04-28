package java7.io.file;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;

public class TestAsyncFileChannel {
	
	/**
	 * 基于主线程轮询的 文件的异步读取
	 * 
	 * AsynchronousFileChannel + Future + Executor
	 *  
	 */
	@Test
	public void testAsyncFuture() {
		try {
			Path file = Paths.get("tmp/input.txt");
			ByteBuffer buffer = ByteBuffer.allocate(100_000_000);
			AsynchronousFileChannel channel = 
					AsynchronousFileChannel.open(file, StandardOpenOption.READ);
			Future<Integer> result = channel.read(buffer, 0);
			
			// 轮询
			while(! result.isDone()) {
				dosomethingelse();
			}
			Integer bytesRead = result.get();
			System.out.println("Bytes read: " + bytesRead);
		} catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	private static void dosomethingelse() {
		System.out.println("读取数据完成之前，可以干点别的工作");
	}
	
	/**
	 * 基于回调方式完成文件的异步读取
	 * 
	 * AsynchronousFileChannel + CompletionHandler
	 * 
	 */
	@Test
	public void testAsyncCompletionHandler() {
		try {
			Path file = Paths.get("tmp/input.txt");
			ByteBuffer buffer = ByteBuffer.allocate(100_000_000);
			AsynchronousFileChannel channel = 
					AsynchronousFileChannel.open(file, StandardOpenOption.READ);
			channel.read(buffer, 0, file, new CompletionHandler<Integer, Path>() {

				@Override
				public void completed(Integer result, Path attachment) {
					System.out.println("Path=" + attachment + ",Bytes read: " + result);
				}

				@Override
				public void failed(Throwable exc, Path attachment) {
					exc.printStackTrace();
				}
			});
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}	
