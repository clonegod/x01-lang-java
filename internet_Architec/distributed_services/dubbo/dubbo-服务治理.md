## dubbo 服务治理

dubbo服务都是以集群方式部署，一个服务部署多个节点来提供负载均衡。

dubbo提供了很多参数来对服务进行细粒度的配置，包括失败重试、最大请求连接数、节点权重、负载均衡策略等等

通过对这些参数进行配置来实现**“服务治理”**，这些参数的配置可以在两个地方进行配置：

	1、项目中的dubbo配置文件（固定配置）

	2、dubbo-admin控制台（动态配置）

## 常用配置参数  

### retries
	失败自动切换，当出现失败，重试其它服务。
	0 表示不重试，等效于配置cluster=failfast模式
	2 设置失败后会调用该服务的其它节点，最多重试2次（缺省）

### cluster 集群模式
	Failover 失败自动切换，当出现失败时，重试其它服务（缺省）。
	Failfast 快速失败，只发起1次调用，失败立即报错。通常用于非幂等性的写操作，比如新增记录。

### 配置服务不同节点的权重（dubbo-admin上对服务进行配置）
	可为同一个服务的不同节点分别配置权重值
	比如NODE1设置为100,NODE2设置为200，则NODE2处理的请求量约为NODE1的2倍。

### LoadBalance （负载均衡策略建议直接在dubbo-admin控制台进行配置）
	注：负载均衡的策略，可在Service层面进行全局设置，也可以细粒度到方法级别上配置！

	Random 随机，按权重值设置随机概率，调用量越大分布越均匀（推荐）
	
	RoundRobin 轮循，按权重设置循环比率。如果某台机器处理任务很慢，容易发生任务堆积的问题。
	
	LeastActive 根据服务器处理任务的耗时，对慢的服务提供者分配更少的请求
	
	ConsistentHash 基于一致性hash算法进行分配，将相同参数的请求分配到同一个服务节点。
	当某个服务挂了，将基于虚拟节点，将请求平摊到其它提供者，不会引起整个服务集群的不均衡。

### 线程模型
	Dispatcher
		all 所有消息都派发到线程池，包括请求，响应，连接事件，断开事件，心跳等。
	
	ThreadPool
		fixed 固定大小线程池，启动时建立线程，不关闭，一直持有。(缺省)
```
<dubbo:protocol name="dubbo" dispatcher="all" threadpool="fixed" threads="100" />	
```

### 连接控制
	限制客户端服务使用连接不能超过多少个
	    <dubbo:service interface="clonegod.dubbo.api.IUserService"  connections="100" />

### 服务多版本
	多版本服务并存，客户端通过指定版本来使用服务的不同版本


### 令牌认证
	客户端调用服务时需要携带token，防止消费者绕过注册中心访问提供者

### 更多配置，参考官方文档...
更多更详细的参数配置，参考官方文档：
[dubbo-user-book](http://dubbo.io/books/dubbo-user-book/demos/preflight-check.html)	

