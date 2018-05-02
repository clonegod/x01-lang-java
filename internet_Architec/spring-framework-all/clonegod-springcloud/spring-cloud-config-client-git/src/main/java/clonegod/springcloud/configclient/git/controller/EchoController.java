package clonegod.springcloud.configclient.git.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope // 当上下文环境中的配置项发生变化时，关联的Bean的属性字段也将发生变化
public class EchoController {

	@Value("${my.name}") // 该属性是从 config server中读取的，因此需要先启动config server
	private String myName;
	
	
	@GetMapping("/my-name")
	public String getName() {
		return myName;
	}
	
}
