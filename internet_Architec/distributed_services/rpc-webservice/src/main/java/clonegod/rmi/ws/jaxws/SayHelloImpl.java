package clonegod.rmi.ws.jaxws;

import javax.jws.WebService;

@WebService
public class SayHelloImpl implements ISayHello {

	@Override
	public String sayHello(String name) {
		System.out.println("接收到客户端请求，name=" + name);
		return "你好，" + name;
	}

}
