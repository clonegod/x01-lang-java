package clonegod.rmi.mock.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import clonegod.rmi.mock.api.User;

/**
 * Client端使用的代理对象，屏蔽底层socket通信
 * 
 */
public class User_Stub extends User {
	
	private Socket socket;

	public User_Stub() throws IOException {
		socket = new Socket("localhost", 9000);
	}

	@Override
	public int getAge() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject("getAge");
			oos.flush();
			
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			int age = ois.readInt();
			
			return age;
			
		} catch (IOException e) {
			throw new RuntimeException("RemoteException", e);
		} finally {
			close();
		}
	}
	
	public void close() {
		try {
			if(socket != null) socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
