package clonegod.broadcast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class BroadcastServer {

	public static void main(String[] args) {
		DatagramSocket ds = null;
		try {
			//广播地址  255.255.255.255
			InetAddress inetAddr = InetAddress.getByName("255.255.255.255");
			//广播的目的端口
			int port = 9999;
			
			ds = new DatagramSocket();
			
			for (int i = 0; i < 10; i++) {
				//广播的消息
				String msg = "[Broadcast] Hello Mic" + i;
				byte[] bytes = msg.getBytes();
				DatagramPacket dp = new DatagramPacket(bytes, bytes.length, inetAddr, port);
				ds.send(dp);
				TimeUnit.SECONDS.sleep(2);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			ds.close();
		}
 	}
}
