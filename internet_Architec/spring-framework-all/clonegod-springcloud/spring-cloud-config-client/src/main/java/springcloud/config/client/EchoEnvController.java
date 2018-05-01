package springcloud.config.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoEnvController {

	private final Environment env;	
	
	@Autowired
	public EchoEnvController(Environment environment) {
		this.env = environment;
	}
	
	/**
	 * 返回系统中存储的环境变量
	 * 
	 * @param param
	 * @return
	 */
	@GetMapping("echo/{param}")
	public Map<String,String> echo(@PathVariable String param) {
		
		Map<String, String> map = new HashMap<>();
		
		map.put(param, env.getProperty(param));
	
		return map;
	}
	
}
