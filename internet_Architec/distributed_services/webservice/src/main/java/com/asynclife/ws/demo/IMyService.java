package com.asynclife.ws.demo;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * SEI: Service Endpoint Interface
 *  服务切入点接口
 */

//可以通过targetNamespace属性设置发布后的webservice命名空间
@WebService(targetNamespace="http://www.asynclife.com/ws/soap")
public interface IMyService {
	
	@WebResult(name="addResult")
	public long add(@WebParam(name="num1") int num1, @WebParam(name="num2")int num2);
	
}
