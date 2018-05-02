package clonegod.springcloud.configclient.git.health;

import org.springframework.boot.actuate.health.ApplicationHealthIndicator;
import org.springframework.boot.actuate.health.Health;

public class MyApplicationHealthIndicator extends ApplicationHealthIndicator {
	
	/**
	 * 
	 */
	@Override
	protected void doHealthCheck(Health.Builder builder) throws Exception {
		builder
			.up()
			.withDetail("totalMemory", Runtime.getRuntime().totalMemory())
			.withDetail("freeMemory", Runtime.getRuntime().freeMemory())
			.withDetail("maxMemory", Runtime.getRuntime().maxMemory());
	}

}
