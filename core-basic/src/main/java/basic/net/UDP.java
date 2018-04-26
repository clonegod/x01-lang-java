package basic.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * 将数据、源地址、目标地址封装到数据包中，发送时不需要建立连接
 * 每个数据包的大小限制在64KB
 * 无连接的，因此是不可靠的
 * 不需要建立连接，因此数据传输速度快
 * @author Administrator
 *
 */
public class UDP {

	public static void main(String[] args) throws Exception {
		/**
		 * UDP的特点是既可以接受数据，又可以发送数据
		 * 1. 发送数据的线程
		 * 2. 接收数据的线程
		 * 3. 广播地址： ip地址最后1位设置为255
		 */
		
		int port = 1000;
		
		// 设置接收方的端口
		new Recver(new DatagramSocket(port)).start();

		// 发送方需要知道接收方的端口
		new Sender(new DatagramSocket(), port).start();
		
		
	}
	
	static class Recver implements Runnable {
		DatagramSocket ds;
		public Recver(DatagramSocket ds) {
			this.ds = ds;
		}
		
		@Override
		public void run() {
			try {
				while(true)
					receive();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println("Server stop");
				ds.close();
			}
		}
		
		public void start() {
			new Thread(this).start();
		}
		
		public void receive() throws Exception {
			
			// 准备接收数据的数据包
			byte[] buf = new byte[1024];
			DatagramPacket recPkg = new DatagramPacket(buf, buf.length);
			
			ds.receive(recPkg);
			
			String ip = recPkg.getAddress().getHostAddress();
			int port = recPkg.getPort();
			
			String msg = new String(recPkg.getData(), 0, recPkg.getLength());
			
			System.out.println("server receive data: "+msg+"(from "+ip+":"+port+")");
			
		}

	}
	
	/**
	 * 以广播方式（同一网段-255）发送数据
	 * @author Administrator
	 *
	 */
	static class Sender implements Runnable {
		
		DatagramSocket ds;
		int recv_port;
		
		public Sender(DatagramSocket ds, int port) {
			this.ds = ds;
			recv_port = port;
		}
		
		@Override
		public void run() {
			try {
				for(int i=0; i<3; i++) {
					Thread.sleep(1000);
					send();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("client exit");
			ds.close();
		}
		
		public void start() {
			new Thread(this).start();
		}
		
		public void send() throws Exception {
			String text = "hello udp";
			
			byte[] data = text.getBytes();
			int length = data.length;
			
			// *.*.*.255 广播地址
			SocketAddress targetAddr = new InetSocketAddress("127.0.0.255", recv_port);
			
			DatagramPacket dataPkg = new DatagramPacket(data, length, targetAddr);
			
			ds.setSoTimeout(2000);
			ds.send(dataPkg);
			System.out.println("client send data: " + text);
			
		}
	}
}

