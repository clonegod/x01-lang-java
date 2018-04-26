package clonegod.core.socket01.bio.threadpool;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	final static int PORT = 8765;

	public static void main(String[] args) {
		ServerSocket server = null;
		HandlerExecutorPool executorPool = null;
		try {
			server = new ServerSocket(PORT);
			System.out.println("server start");
			Socket socket = null;
			/** 服务端维护一个线程池 */
			executorPool = new HandlerExecutorPool(50, 1000);
			while(true){
				socket = server.accept();
				/** 将请求提交到线程池中进行处理 */
				executorPool.execute(new RequestHandler(socket));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(executorPool != null) {
				try {
					executorPool.shutdown();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(server != null){
				try {
					server.close();
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
			server = null;				
		}
		
	}
	
}
