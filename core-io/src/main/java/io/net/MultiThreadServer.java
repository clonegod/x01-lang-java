package io.net;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadServer {
	
	public static void main(String[] args) throws Exception {
		String ip = InetAddress.getLocalHost().getHostAddress();
		int port = 8000;
		
		new Server(port).start();
		
		new Client(ip, port, 5000, new File("src/test/resources/gtvg.jpg")).start();
		new Client(ip, port, 5000, new File("src/test/resources/stms.jpg")).start();
	}
	
	static class Server implements Runnable {

		static final ExecutorService executor = Executors.newFixedThreadPool(100);
		
		ServerSocket ss;
		
		int port;
		
		public Server(int port) {
			this.port = port;
		}

		public void start() {
			
			try {
				ss = new ServerSocket(port);
				new Thread(this).start();
				System.out.println("server start");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		@Override
		public void run() {
			try {
				while(true) {
					Socket socket = ss.accept();
					executor.submit(new Worker(socket));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// ss.close();
			
		}
	}
	
	static class Worker implements Runnable {

		Socket socket;
		
		public Worker(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			// process socket
			try {
				System.out.println(Thread.currentThread().getName()+" process client upload file");
				
				InputStream in = socket.getInputStream();
				
				byte[] buf = new byte[1024*8];
				
				
				File file = new File(socket.getInetAddress().getHostAddress()+"_"+new Random().nextInt(99)+".jpg");
				file.createNewFile();
				OutputStream out = new FileOutputStream(file);
				
				int len = 0;
				while((len = in.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
				out.close();
				
				// send result to client
				socket.getOutputStream().write("上传成功".getBytes());
				socket.shutdownOutput();
				socket.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	static class Client implements Runnable {
		Socket socket;
		
		String serverIp;
		int serverPort;
		
		File targetFile;
		public Client(String serverIp, int serverPort, int timeout, File file) {
			this.serverIp = serverIp;
			this.serverPort = serverPort;
			
			this.targetFile = file;
			
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
			upload();
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
		
		public void upload() {
			
			try {
				// send data to server
				RandomAccessFile raf = new RandomAccessFile(targetFile, "r");
				FileChannel fc = raf.getChannel();
				
				ByteBuffer byteBuf = ByteBuffer.allocate(1024*16);
				
				while(fc.read(byteBuf) != -1) {
					byteBuf.flip();
					
					socket.getOutputStream().write(byteBuf.array());
					
					byteBuf.clear();
				}
				raf.close();
				fc.close();
				
				socket.shutdownOutput(); // 发送流结束标记给服务器
				
				// try to read data from server
				InputStream in = socket.getInputStream();
				BufferedReader lnr = new BufferedReader(new InputStreamReader(in));
				
				String reply = lnr.readLine();
				System.out.println("server reply: "+reply);
				
				close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
}
