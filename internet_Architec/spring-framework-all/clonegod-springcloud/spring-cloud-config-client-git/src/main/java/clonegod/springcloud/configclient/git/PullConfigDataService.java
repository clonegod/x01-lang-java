package clonegod.springcloud.configclient.git;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PullConfigDataService {
	
	private final RestTemplate restTemplate;
	
	private final Environment env;
	
	@Value("${server.port}")
	private String serverPort;

	@Autowired
	public PullConfigDataService(RestTemplate restTemplate, Environment env) {
		super();
		this.restTemplate = restTemplate;
		this.env = env;
	}

	/**
	 * 配置服务器上的配置信息发生改变后，在客户端主动发起更新请求
	 * 定时请求本地接口：/refresh，实现同步 config server上的 配置信息
	 */
	@Scheduled(initialDelay=1000, fixedDelay=10_000)
	public void refreshConfig() {
		String refreshUri = String.format("http://localhost:%s/refresh", serverPort);
		String result = restTemplate.postForObject(refreshUri, null, String.class);
		System.err.printf("%s ---> %s\n", refreshUri, result);
	}
	
	/**
	 * 定时打印配置参数，验证是否更新参数成功
	 */
	@Scheduled(initialDelay=1000, fixedRate=5000)
	public void printConfig() {
		System.out.println("clients.threshold="+env.getProperty("clients.threshold"));
	}
	
	
}
