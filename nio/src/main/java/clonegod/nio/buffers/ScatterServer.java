package clonegod.nio.buffers;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ScatterServer {
	static private final int firstHeaderLength = 2;
	static private final int secondHeaderLength = 4;
	static private final int bodyLength = 6;

	static public void main(String args[]) throws Exception {
//		if (args.length != 1) {
//			System.err.println("Usage: java UseScatterGather port");
//			System.exit(1);
//		}

		int port = 1234;

		ServerSocketChannel ssc = ServerSocketChannel.open();
		InetSocketAddress address = new InetSocketAddress(port);
		ssc.socket().bind(address);

		int messageLength = firstHeaderLength + secondHeaderLength + bodyLength;

		ByteBuffer buffers[] = new ByteBuffer[3];
		buffers[0] = ByteBuffer.allocate(firstHeaderLength);
		buffers[1] = ByteBuffer.allocate(secondHeaderLength);
		buffers[2] = ByteBuffer.allocate(bodyLength);

		System.out.println( "Going to listen on "+port );
		SocketChannel sc = ssc.accept();

		while (true) {
			if(! sc.isOpen()) {
				break;
			}

			// Scatter-read into buffers
			int bytesRead = 0;
			while (bytesRead < messageLength) {
				long r = sc.read(buffers);
				if(r == -1) {
					sc.close();
					break;
				}
				bytesRead += r;

				System.out.println("read: " + r);
				for (int i = 0; i < buffers.length; ++i) {
					ByteBuffer bb = buffers[i];
					System.out.println("buf-" + i + ", position=" + bb.position() + ", limit="
							+ bb.limit() + " , data=" + new String(bb.array()));
				}
			}

			// Process message here

			// Flip buffers
			for (int i = 0; i < buffers.length; ++i) {
				ByteBuffer bb = buffers[i];
				bb.flip();
			}

			// Scatter-write back out
			long bytesWritten = 0;
			while (bytesWritten < messageLength) {
				if(sc.isOpen()) {
					long r = sc.write(buffers);
					bytesWritten += r;
				} else {
					break;
				}
			}

			// Clear buffers
			for (int i = 0; i < buffers.length; ++i) {
				ByteBuffer bb = buffers[i];
				bb.clear();
			}

			System.out.println(bytesRead + " " + bytesWritten + " "
					+ messageLength);
		}
	}
}
