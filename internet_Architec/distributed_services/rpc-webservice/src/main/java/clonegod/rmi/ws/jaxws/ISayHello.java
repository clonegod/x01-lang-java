package clonegod.rmi.ws.jaxws;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService // SEI中的接口和实现类
public interface ISayHello {
	
	@WebMethod // SEI中对外提供的方法
	public String sayHello(String name);
}
