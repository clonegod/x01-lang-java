package clonegod.rmi.ws.jaxws;

import javax.xml.ws.Endpoint;

public class Bootstrap {
	
	/**
	 * Webservice是通过什么方式启动的服务？
			底层实现:
				com.sun.xml.internal.ws.transport.http.server.EndpointImpl
				private void createEndpoint(String urlPattern) { 
					... 
					Class.forName("com.sun.net.httpserver.HttpServer"); // JDK内置的HttpServer服务器
					... 
				}
	 * 	
	 */
	public static void main(String[] args) {
		/**
		 * 发布违背service服务
		 * 浏览器访问：http://127.0.0.1:9000/vip/sayHello?wsdl
		 */
		Endpoint.publish("http://127.0.0.1:9000/vip/sayHello", new SayHelloImpl());
		
		System.out.println("webservice start success");
	}
}
