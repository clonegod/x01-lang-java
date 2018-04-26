package clonegod.dubbox.restful.consumer;

import java.io.IOException;
import java.util.Map;

import com.google.common.collect.Maps;

import clonegod.dubbox.restful.utils.FastJsonConvert;
import clonegod.dubbox.restful.utils.HttpInvokerUtils;
import clonegod.dubbox.restful.utils.HttpProxy;

public class RestfulConsumer {
	public static void main(String[] args) throws IOException {
		System.out.println("----------测试GET");
		System.out.println(
				HttpInvokerUtils.get("http://192.168.1.105:9000/restfulProvider/userService/testGet", null)
				);
		System.out.println(
				HttpInvokerUtils.get("http://192.168.1.105:9000/restfulProvider/userService/getUser", null)
				);
		System.out.println(
				HttpInvokerUtils.get("http://192.168.1.105:9000/restfulProvider/userService/get/1", null)
				);
		System.out.println(
				HttpInvokerUtils.get("http://192.168.1.105:9000/restfulProvider/userService/get/1/alice", null)
				);
		
		
		Map<String,String> params = Maps.newHashMap();
		params.put("username", "alice");
		params.put("age", "20");
		
		System.out.println("----------测试POST");
		System.out.println(
				HttpProxy.postJson("http://192.168.1.105:9000/restfulProvider/userService/postUser",
						FastJsonConvert.convertObjectToJSON(params))
				);
		
		
		System.out.println("----------测试PUT");
		//TODO
		
		System.out.println("----------测试DELETE");
		//TODO
	}
}
