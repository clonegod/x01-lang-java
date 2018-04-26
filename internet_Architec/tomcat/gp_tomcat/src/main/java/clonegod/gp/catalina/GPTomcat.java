package clonegod.gp.catalina;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import clonegod.gp.http.GPRequest;
import clonegod.gp.http.GPResponse;
import clonegod.gp.http.GPServlet;

public class GPTomcat {
	
	private int port;
	private ServerSocket serverSocket;
	private static final Map<String, GPServlet> SERVLET_MAPPING = new HashMap<>();
	private static final String WEB_INF = GPTomcat.class.getResource("/").getPath() ;
	
	public GPTomcat(int port) {
		super();
		this.port = port;
	}

	/**
	 * 初始化Servlet路由配置
	 */
	public void init() {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(WEB_INF + "web.properties");
			
			Properties webXml = new Properties();
			webXml.load(fis);
			
			for(Object k : webXml.keySet()) {
				String key = (String)k;
				if(key.endsWith("url")) {
					String url = (String)webXml.getProperty(key);
					String servletClass = webXml.getProperty(key.replace(".url", ".class"));
					GPServlet servlet = (GPServlet) Class.forName(servletClass).newInstance();
					SERVLET_MAPPING.put(url, servlet);
				}
			}
			
			System.out.println(SERVLET_MAPPING);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try { fis.close(); } catch (IOException e) { e.printStackTrace(); }
			}
		}
	}
	
	public void start() {
		init();
		
		// 启动服务端Socket，开始监听客户端请求
		try {
			serverSocket = new ServerSocket(this.port);
			System.out.println("Tomcat 已启动，监听端口：" + this.port);
		} catch (Exception e) {
			System.out.println("GP Tomcat 启动失败，" + e.getMessage());
			return;
		}
		
		// server容器 一直运行提供服务
		while(true) {
			try(Socket socket = serverSocket.accept();
				InputStream in = socket.getInputStream();
				OutputStream out = socket.getOutputStream();) {
				
				dispatch(in, out); // 处理请求，响应结果
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
			}
		}
	}
	
	private void dispatch(InputStream in, OutputStream out) throws Exception {
		GPRequest request = new GPRequest(in);
		GPResponse response = new GPResponse(out);
		
		String url = request.getUrl();
		
		if(! SERVLET_MAPPING.containsKey(url)) {
			response.write("404 - Not Found");
			return;
		}
		
		GPServlet servlet = SERVLET_MAPPING.get(url);
		
		
		servlet.service(request, response);
	}

	/**
	 * 启动浏览器，访问 http://localhost:8080/first.do
	 * 
	 * 使用IE浏览器测试（chrome不行，chrome认为返回的数据格式不规范）
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GPTomcat tomcat = new GPTomcat(8080);
		tomcat.start();
	}
}
