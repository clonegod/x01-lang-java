package io.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * 必须先建立数据传输通道，才能发送数据
 * 连接之后可进行大量数据传输
 * 通过三次握手完成连接，是可靠的协议
 * 必须建立连接，效率稍低，但可保持连接后数据的完整传输
 * 
 * socket.shutdownInput(); // 置入输入流结束标记，解除readLine的阻塞
 * socket.shutdownOutput(); // 置入输出流结束标记，解除readLine的阻塞
 * @author Administrator
 *
 */
public class TCP {

	public static void main(String[] args) throws Exception {
		
		String serverIp = InetAddress.getLocalHost().getHostAddress();
		int serverPort = 10010;
		
		new Server(serverPort).listen();
		
		new Client(serverIp, serverPort, 3000).start();
	}
	
	/**
	 * 服务端提供接收数据服务
	 * 
	 * @author Administrator
	 *
	 */
	static class Server implements Runnable {
		
		ServerSocket serverSocket;
		
		public Server(int port) {
			try {
				this.serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void listen() {
			new Thread(this).start();
		}
		
		@Override
		public void run() {
			for(;;) {
				try {
					Socket socket = serverSocket.accept(); // block until one client arrive
					
					System.out.println(socket.getInetAddress().getHostAddress()+","+socket.getPort());
					
					handleReq(socket);
					
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}

		private void handleReq(Socket socket) throws Exception {
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
			
			InputStream in = socket.getInputStream();
			BufferedReader lnr = new BufferedReader(new InputStreamReader(in));
			
			String line = null;
			while((line=lnr.readLine()) != null) {
				System.out.println("client say: "+line);
				pw.println("echo " + line.toUpperCase());
			}
			
			// release resource
			System.out.println("server close socket: "+socket.hashCode());
			socket.close(); 
		}
		
	}
	
	/**
	 * 客户端通过键盘录入获取数据，发送到服务端，并接收服务端的响应
	 * 当服务端接收到over后，关闭与客户端关联的socket
	 * 客户端关闭socket，退出
	 * @author Administrator
	 *
	 */
	static class Client implements Runnable {
		Socket socket;
		
		String serverIp;
		int serverPort;
		public Client(String serverIp, int serverPort, int timeout) {
			this.serverIp = serverIp;
			this.serverPort = serverPort;
			
			socket = new Socket();
			SocketAddress endpoint = new InetSocketAddress(serverIp, serverPort);
			try {
				socket.connect(endpoint, timeout);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void start() {
			new Thread(this).start();
		}
		
		@Override
		public void run() {
			send();
			System.out.println("client exit");
		}
		
		public void close() {
			try {
				System.out.println("client close socket: " + socket.hashCode());
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void send() {
			
			try {
				// send data to server
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				
				BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));	
				String line = null;
				while((line=bufr.readLine()) != null) {
					if(line.matches("[oO]ver")) {
						socket.shutdownOutput(); // 置入流结束标记
						break;
					}
					
					pw.println(line);
					
					// try to read data from server
					InputStream in = socket.getInputStream();
					BufferedReader lnr = new BufferedReader(new InputStreamReader(in));
					
					String reply = lnr.readLine();
					System.out.println("server reply: "+reply);
				}
				close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	
}
