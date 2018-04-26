package clonegod.nio.channel.socketchannel;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;

public class NIOServer {
	
	String filename = "bigfile.txt"; // a big file

	// 通过一个Handler来获取文件的一块数据，每一个客户都会分配一个HandleClient的实例
	private class BigFileHandler {
		FileChannel fc;
		ByteBuffer byteBuffer;

		@SuppressWarnings("resource")
		public BigFileHandler() throws IOException {
			this.fc = new FileInputStream(filename).getChannel();
			this.byteBuffer = ByteBuffer.allocate(block_size);
		}

		public ByteBuffer readBlock() {
			try {
				byteBuffer.clear();

				int count = fc.read(byteBuffer);
				if (count <= 0) {
					return null;
				}
				byteBuffer.flip();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			
			ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
			
			return readOnlyBuffer;
		}

		public void close() {
			try {
				this.fc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	
	static int block_size = 1024 * 4; // 4K
	Selector selector;
	ByteBuffer clientBuffer = ByteBuffer.allocate(block_size);
	CharsetDecoder decoder;

	public NIOServer(int port) throws IOException {
		this.selector = getSelector(port);
		Charset charset = Charset.forName("UTF-8");
		this.decoder = charset.newDecoder();
	}

	private Selector getSelector(int port) throws IOException {
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.configureBlocking(false);

		ServerSocket ss = ssc.socket();
		ss.bind(new InetSocketAddress(port));

		// Create a new selector
		Selector selector = Selector.open();

		// 首先，注册Accept事件
		ssc.register(selector, SelectionKey.OP_ACCEPT);

		return selector;
	}

	public void listen() {
		try {
			for (;;) {
				int n = selector.select(); // 选择已就绪的通道
				if(n == 0) {
					continue;
				}
				
				Iterator<SelectionKey> iter = selector.selectedKeys()
						.iterator();
				
				while (iter.hasNext()) {
					SelectionKey key = iter.next();
					handleKey(key);
					iter.remove();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void handleKey(SelectionKey key) throws IOException {
		if (key.isAcceptable()) { // Accept the new connection
			ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
			SocketChannel sc = ssc.accept();
			sc.configureBlocking(false);
			sc.register(selector, SelectionKey.OP_READ); // 为该channel注册读事件到selector
			
		} else if (key.isReadable()) { // Read the data
			SocketChannel sc = (SocketChannel) key.channel();
			clientBuffer.clear();
			int count = sc.read(clientBuffer);
			if (count <= 0) {
				System.out.println("Read data from channel is less than 0, close channel " + sc.hashCode());
				sc.close(); // >>>??? 
				return;
			}
			clientBuffer.flip();
			CharBuffer charBuf = decoder.decode(clientBuffer);
			
			System.out.println("Read data from channel"+sc.hashCode()+" >>" + charBuf.toString());
			
			SelectionKey wKey = sc.register(selector,
					SelectionKey.OP_WRITE);
			wKey.attach(new BigFileHandler());
			
		} else if (key.isWritable()) { // Write the data
			SocketChannel socketChannel = (SocketChannel) key.channel();
			BigFileHandler handle = (BigFileHandler) key.attachment();
			ByteBuffer block = handle.readBlock();
			if (block != null) {
				socketChannel.write(block);
			} else {
				System.out.println("关闭FileChannel");
				handle.close();
				System.out.println("关闭socketChannel:"+socketChannel.hashCode());
				socketChannel.close();
			}
			
		}
	}

	public static void main(String[] args) throws IOException {
		int port = 12345;
		NIOServer server = new NIOServer(port);
		while(true) {
			System.out.println("[Server start listening]");
			server.listen();
		}
		
	}
}
