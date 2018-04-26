package clonegod.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Demonstrate asynchronous connection of a SocketChannel.
 * 
 * @author Ron Hitchens (ron@ronsoft.com)
 */
public class ConnectAsyncClient {
	
	public static void main(String[] argv) throws Exception {
		String host = "localhost";
		int port = 80;
		if (argv.length == 2) {
			host = argv[0];
			port = Integer.parseInt(argv[1]);
		}
		InetSocketAddress addr = new InetSocketAddress(host, port);
		SocketChannel sc = SocketChannel.open();
		sc.configureBlocking(false);
		
		System.out.println("initiating connection");
		sc.connect(addr);
		while (!sc.finishConnect()) {
			doSomethingUseful();
		}
		
		System.out.println("connection established");
		// Do something with the connected socket
		// The SocketChannel is still nonblocking
		
		readDataFromServer(sc);
		
		sc.close();
	}

	private static void readDataFromServer(SocketChannel sc) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		while((sc.read(buffer)) > 0) {
			buffer.flip();
			System.out.println(new String(buffer.array()));
			buffer.clear();
		}
		
	}

	private static void doSomethingUseful() {
		System.out.println("doing something useless");
	}
}