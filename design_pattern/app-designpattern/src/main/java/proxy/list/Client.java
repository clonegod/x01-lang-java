package proxy.list;

import java.util.ArrayList;
import java.util.List;

public class Client {
	public static void main(String[] args) {
		
		List proxyTarget = new ArrayList();
		
		ListProxyFactory proxy = new ListProxyFactory(proxyTarget);
		
		List proxyList = (List)proxy.getProxy();
		
		proxyList.add("aaaaaaaaaaaaaaaaaaaa");
		proxyList.add("bbbbbbbbbbbbbbbbbbbb");
		
		proxyList.remove(1);
		
	}
}
