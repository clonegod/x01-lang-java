package clonegod.rmi.ws.jaxws.client;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import clonegod.rmi.ws.jaxws.ISayHello;

public class WSClient2 {
	/**
	 * 不往客户端添加任何代码，调用 Web Services 服务：
	 * 最接近的的是，在客户端放入服务端的接口类，手动配置webservice相关参数。
	 * 
	 */
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://127.0.0.1:9000/vip/sayHello?wsdl");
        QName serviceName = new QName("http://jaxws.ws.rmi.clonegod/", "SayHelloImplService");
        QName portName = new QName("http://jaxws.ws.rmi.clonegod/", "SayHelloImplPort");

        Service service = Service.create(url, serviceName);
        ISayHello servicePort = service.getPort(portName, ISayHello.class);
        System.out.println(servicePort.sayHello("mic"));
	}
}
