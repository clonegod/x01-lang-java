package clonegod.broadcast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class BroadcastClient {

	public static void main(String[] args) throws Exception {
		DatagramSocket socket = null;
		try {
			int port = 9999; //监听的端口
			socket = new DatagramSocket(port);

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
