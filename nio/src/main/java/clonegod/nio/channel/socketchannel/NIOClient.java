package clonegod.nio.channel.socketchannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NIOClient {

	static int closed = 0;
	
	static int wokerCount = 1;
	static InetSocketAddress remote = new InetSocketAddress("localhost", 12345);;
	static CharsetEncoder encoder = Charset.forName("GB2312").newEncoder();;

	static class Worker implements Runnable {
		int wokerNo;

		public Worker(int wokerNo) {
			this.wokerNo = wokerNo;
		}

		public void run() {
			try {
				long startMills = System.currentTimeMillis();

				getRemoteFile();

				long endMills = System.currentTimeMillis();
				System.out.println(wokerNo + " takes:" + (endMills - startMills));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void getRemoteFile() throws IOException {

			SocketChannel sc = SocketChannel.open();
			sc.configureBlocking(false);

			Selector selector = Selector.open();
			sc.register(selector, SelectionKey.OP_CONNECT);

			sc.connect(remote);

			for (;;) {
				System.out.println(closed+"-"+wokerCount);
				if(closed == wokerCount) {
					break;
				}
				selector.select();

				Iterator<SelectionKey> iter = selector.selectedKeys()
						.iterator();

				while (iter.hasNext()) {
					SelectionKey key = iter.next();
					handleKey(key);
					iter.remove();
				}
			}

		}

		private void handleKey(SelectionKey key) throws IOException {
			if (key.isConnectable()) {
				SocketChannel channel = (SocketChannel) key.channel();
				if (channel.isConnectionPending()) {
					channel.finishConnect();
					channel.write(encoder.encode(CharBuffer.wrap("Client Worker No: "
							+ this.wokerNo)));
					channel.register(key.selector(), SelectionKey.OP_READ);
				}
			} else if (key.isReadable()) {
				ByteBuffer buffer = ByteBuffer.allocate(8 * 1024);
				buffer.clear();
				SocketChannel channel = (SocketChannel) key.channel();
				int count = channel.read(buffer);
				if (count <= 0) {
					channel.close();
					closed++;
				}
			}

		}
	}

	public static void main(String[] args) {
		ExecutorService exec = Executors.newFixedThreadPool(wokerCount);
		for (int i = 1; i <= wokerCount; i++) {
			exec.execute(new Worker(i));
		}
		exec.shutdown();
	}

}
