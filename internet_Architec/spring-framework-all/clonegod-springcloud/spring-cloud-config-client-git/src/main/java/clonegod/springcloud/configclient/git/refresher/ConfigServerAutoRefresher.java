package clonegod.springcloud.configclient.git.refresher;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * 客户端定时从 config server上同步最新的配置
 *
 */
@Service
public class ConfigServerAutoRefresher {
	
	private final ContextRefresher contextRefresher;
	
	private final Environment env;

	@Autowired
	public ConfigServerAutoRefresher(ContextRefresher contextRefresher, Environment env) {
		this.contextRefresher = contextRefresher;
		this.env = env;
	}

	/**
	 * 配置服务器上的配置信息发生改变后，在客户端主动发起更新请求
	 * 	第1种方式：定时调用contextRefresher.refresh()  --- 缺点：会导致应用重启
	 * 	第2种方式：定时请求本地接口：/refresh，实现同步 config server上的 配置信息 --- 缺点：硬编码
	 */
	@Scheduled(initialDelay = 3 * 1000, fixedDelay= 5 * 1000)
	public void autoRefresh() {
		Set<String> updatedPropertyNames = contextRefresher.refresh();
		
		updatedPropertyNames.forEach(key -> {
			System.out.printf("[Thread - %s] 更新配置成功，key=%s, value=%s\n", 
					Thread.currentThread().getName(),
					key,
					env.getProperty(key));
		});
	}
	
	
	
	final RestTemplate template = new RestTemplate();
	
	// @Scheduled(initialDelay = 3 * 1000, fixedDelay= 5 * 1000)
	public void autoRefreshByHttpInvoke() {
		String updatedKeys = template.postForObject("http://localhost:8080/refresh", null, String.class);
		if(! StringUtils.isEmpty(updatedKeys)) {
			System.out.printf("[Thread - %s] 更新配置成功：%s\n", 
					Thread.currentThread().getName(),
					updatedKeys);
		}
	}
	
	
}
