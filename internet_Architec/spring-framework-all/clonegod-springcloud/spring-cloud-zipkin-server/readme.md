## 搭建 Zipkin Server

### 加入依赖
		<!-- Zipkin server 服务支持-->
		<dependency>
		    <groupId>io.zipkin.java</groupId>
		    <artifactId>zipkin-server</artifactId>
		</dependency>
		
		<!-- Zipkin UI 界面支持-->
		<dependency>
			<groupId>io.zipkin.java</groupId>
			<artifactId>zipkin-autoconfigure-ui</artifactId>
		</dependency>
		
		
### 激活Zipkin自动配置
	@SpringBootApplication
	@EnableZipkinServer
	//@EnableZipkinStreamServer
	public class SpringCloudZipkinServerApplication {
	
		public static void main(String[] args) {
			SpringApplication.run(SpringCloudZipkinServerApplication.class, args);
		}
	}