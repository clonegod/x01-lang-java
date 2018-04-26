package clonegod.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastClient {

	public static void main(String[] args) throws 		Exception {
		MulticastSocket socket = null;
		try {
			final InetAddress group = InetAddress.getByName("224.5.6.7");
			
			socket = new MulticastSocket(8888); // 绑定组播端口
			socket.joinGroup(group); // 加到指定的组

			byte[] buf = new byte[256];
			while (true) {
				DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
				socket.receive(msgPacket);

				String msg = new String(msgPacket.getData());
				System.out.println("接收到的数据：" + msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			socket.close();
		}

	}
}
