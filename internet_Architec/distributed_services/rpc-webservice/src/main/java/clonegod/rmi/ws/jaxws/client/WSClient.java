package clonegod.rmi.ws.jaxws.client;

import clonegod.wsclient.generated.SayHelloImpl;
import clonegod.wsclient.generated.SayHelloImplService;

public class WSClient {
	public static void main(String[] args) {
		
		SayHelloImpl helloService = new SayHelloImplService().getSayHelloImplPort();
		
		String response = helloService.sayHello("菲菲");
		
		System.out.println(response);
	}
}
