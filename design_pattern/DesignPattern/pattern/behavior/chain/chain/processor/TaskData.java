package chain.processor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TaskData {
	
	RequestBean req = new RequestBean();
	
	ResponseBean res = new ResponseBean();
	
	boolean complete = false;
	
	public boolean isComplete() {
		return complete;
	}
	
	public void setResult(String key, Object value) {
		res.data.put(key, value);
	}
	
	public Object getRequestParam(String key) {
		return req.data.get(key);
	}

	private static class ResponseBean {
		
		private Map<String, Object> data = new LinkedHashMap<>();

		@Override
		public String toString() {
			return data.toString();
		}
	}
	
	private static class RequestBean {
		private Map<String, Object> data = new HashMap<>();
		
	}
}
