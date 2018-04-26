package clonegod.gp.http;

public abstract class GPServlet {
	/**
	 * 设计模式---模板方法
	 *  父类：定义流程骨架；
	 * 	子类：重写doGet和doPost，处理各自具体的业务逻辑；
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void service(GPRequest request, GPResponse response) throws Exception {
		if("GET".equalsIgnoreCase(request.getMethod())) {
			doGet(request, response);
		} else {
			doPost(request, response);
		}
	}
	
	protected abstract void doGet(GPRequest request, GPResponse response) throws Exception;
	
	protected abstract void doPost(GPRequest request, GPResponse response) throws Exception;
	
}
