package clonegod.gp.servlet;

import java.io.IOException;

import clonegod.gp.http.GPRequest;
import clonegod.gp.http.GPResponse;
import clonegod.gp.http.GPServlet;

public class FirstServlet extends GPServlet {

	@Override
	protected void doGet(GPRequest request, GPResponse response) throws IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(GPRequest request, GPResponse response) throws IOException {
		// 接收到客户端请求后，开始处理业务逻辑
		
		response.write("Hello GP EDU" + ",method= " + request.getMethod() + ",url=" + request.getUrl());
	}

}
