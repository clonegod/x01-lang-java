package juc.executor;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkService implements Runnable {
	private final ServerSocket serverSocket;
	private final ExecutorService pool;

	public NetworkService(int port, int poolSize) throws IOException {
		serverSocket = new ServerSocket(port);
		pool = Executors.newFixedThreadPool(poolSize);
	}

	public void run() { // run the service
		try {
			for (;;) {
				pool.execute(new Handler(serverSocket.accept()));
			}
		} catch (IOException ex) {
			pool.shutdown();
		}
	}
}


class Handler implements Runnable {
	private Socket socket;

	Handler(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		// read and service request on socket
		try {
			InputStream in = socket.getInputStream();
			
			in.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}