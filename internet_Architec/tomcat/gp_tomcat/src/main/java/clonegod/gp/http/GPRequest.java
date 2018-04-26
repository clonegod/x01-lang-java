package clonegod.gp.http;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class GPRequest {
	
	private InputStream in;
	
	private String method;
	private String url;
	private Map<String,String> paramters = new HashMap<>();

	public GPRequest(InputStream in) {
		this.in = in;
		process();
	}

	/**
	 * 解析客户端请求 
	 */
	private void process() {
		try {
			String content = "";
			
			byte[] buf = new byte[1024];
			int len = 0;
			if((len = this.in.read(buf)) > 0) {
				content = new String(buf, 0, len, StandardCharsets.UTF_8.name());
			} else {
				return;
			}
			
			System.out.println(content);
			
			// 从HTTP协议头的中解析相关信息
			String firstLine = content.split("\\n")[0];
			
			String[] array = firstLine.split("\\s");
			this.method = array[0];
			this.url = array[1];
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getMethod() {
		return method;
	}

	public String getUrl() {
		return url;
	}

	public Map<String, String> getParamters() {
		return paramters;
	}
	
	
}
