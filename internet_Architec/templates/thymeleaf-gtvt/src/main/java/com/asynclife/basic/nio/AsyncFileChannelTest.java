package com.asynclife.basic.nio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

import org.junit.Test;

/**
 * The AsynchronousFileChannel makes it possible to read data from, and write data to files asynchronously
 *
 */
public class AsyncFileChannelTest {

	/**
	 * 异步读-Future
	 * @throws Exception
	 */
	@Test
	public void testReadData() throws Exception {
		Path path = Paths.get("src/test/resources/data/test-read.txt");

		AsynchronousFileChannel fileChannel =
		    AsynchronousFileChannel.open(path, StandardOpenOption.READ);
		
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		long position = 0;

		Future<Integer> operation = fileChannel.read(buffer, position);

		//the read operation is finished by calling the isDone() method of the Future instance
		while(!operation.isDone());

		buffer.flip();
		byte[] data = new byte[buffer.remaining()];
		buffer.get(data, 0, buffer.remaining());
		System.out.println(new String(data));
		buffer.clear();
	}
	
	
	/**
	 * 异步读-CompletionHandler
	 * @throws Exception
	 */
	@Test
	public void testReadDataViaCompletionHandler() throws Exception {
		Path path = Paths.get("src/test/resources/data/test-read.txt");

		AsynchronousFileChannel fileChannel =
		    AsynchronousFileChannel.open(path, StandardOpenOption.READ);
		
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		long position = 0;

		Future<Integer> operation = fileChannel.read(buffer, position);

		//the read operation is finished by calling the isDone() method of the Future instance
		while(!operation.isDone());
		
		fileChannel.read(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {
		    @Override 
		    public void completed(Integer count, ByteBuffer attachment) {
		        System.out.println("bytes read = " + count);

		        attachment.flip();
		        System.out.println("attachment.limit() = " + attachment.limit());
		        byte[] data = new byte[attachment.limit()];
		        attachment.get(data);
		        System.out.println(new String(data));
		        attachment.clear();
		    }

		    @Override
		    public void failed(Throwable exc, ByteBuffer attachment) {
		    	exc.printStackTrace();
		    }
		});
	}
	
	
	/**
	 * 异步写文件- Future
	 * @throws Exception
	 */
	@Test
	public void testWriteDataViaFuture() throws Exception {
		Path path = Paths.get("src/test/resources/data/test-write.txt");
		if(!Files.exists(path)){
		    Files.createFile(path);
		}
		
		AsynchronousFileChannel fileChannel = 
		    AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);

		ByteBuffer buffer = ByteBuffer.allocate(1024);
		long position = 0;

		buffer.put("test data".getBytes());
		buffer.flip();

		Future<Integer> operation = fileChannel.write(buffer, position);
		buffer.clear();

		while(!operation.isDone());

		System.out.println("Write done");
		
	}
	
	/**
	 * 异步写文件-CompletionHandler
	 * @throws Exception
	 */
	@Test
	public void testWriteDataViaCompletionHandler() throws Exception {
		Path path = Paths.get("src/test/resources/data/test-write.txt");
		if(!Files.exists(path)){
		    Files.createFile(path);
		}
		AsynchronousFileChannel fileChannel = 
		    AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);

		ByteBuffer buffer = ByteBuffer.allocate(1024);
		long position = 0;

		buffer.put("测试异步写文件".getBytes());
		buffer.flip();

		fileChannel.write(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {

		    @Override
		    public void completed(Integer result, ByteBuffer attachment) {
		        System.out.println("bytes written: " + result);
		    }

		    @Override
		    public void failed(Throwable exc, ByteBuffer attachment) {
		        System.out.println("Write failed");
		        exc.printStackTrace();
		    }
		});
	}
	
	
}
