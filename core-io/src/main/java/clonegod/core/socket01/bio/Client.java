package clonegod.core.socket01.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	final static String ADDRESS = "127.0.0.1";
	final static int PORT = 8765;
	
	public static void main(String[] args) {
		
		Socket socket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		
		try {
			socket = new Socket(ADDRESS, PORT);
			
			// 向服务器端发送数据
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("I am coming...");
			
			// 读取服务端的响应
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = in.readLine();
			System.out.println("Client Receive Message: " + response);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out != null){
				try {
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(socket != null){
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			socket = null;
		}
	}
}
