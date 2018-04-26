package clonegod.rmi.mock.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import clonegod.rmi.mock.api.User;

/**
 * Server端对外提供服务的代理对象，屏蔽底层socket通信 
 *
 */
public class User_Skeleton extends Thread {

	User user;
	
	public User_Skeleton(User user) {
		super();
		this.user = user;
	}

	@Override
	public void run() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(9000);
			System.out.println("Server start success");
			
			Socket socket = serverSocket.accept();
			
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			
			int age = -1;
			
			String methodName = (String)ois.readObject();
			if("getAge".equals(methodName)) {
				age = user.getAge();
			}
			
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeInt(age);
			oos.flush();
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
