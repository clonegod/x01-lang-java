## zookeeper客户端
>zookeeper 原生API
>
>zkClient 开源第三方框架
>
>curator框架 [kjʊˈreɪtə(r)] 更强大的第三方框架


## zookeeper 原生API
	1、创建节点
		节点分4种类型
			持久化节点 PERSISTENT
			持久化顺序节点 PERSISTENT_SEQUENTIAL
			临时性节点 EPHEMERAL
			临时性顺序节点 EPHEMERAL_SEQUENTIAL

	2、修改节点value
		this.zk.setData(path, data.getBytes(), -1) // -1 表示节点的任何版本号

	3、获取节点value
		zk.getData(path, needWatch, null)

	4、删除节点
		zk.delete(path, -1); // -1 表示节点的任何版本号

	5、watcher - 触发1次后就失效性，之后需要重新注册watcher
		5种WatchedEvent:
			None	初始连接成功的事件
			NodeCreated	 节点创建
			NodeDataChanged 简单数据发生修改
			NodeChildrenChanged 仅告知子节点发生变化，但具体何种事件不明确
			NodeDeleted	节点删除


## zkClient - 封装zookeeper原生API，提供更便捷的接口
创建zkClient对象实例的构造参数

	zkServers	zookeeper集群地址，节点间用","分隔
	sessionTimeout	会话超时时间，毫秒，默认30000ms
	connectiontimeout	连接超时时间，毫秒
	IZkConnection		接口实现类
	zkSerializer		自定义序列化实现

简化了原生zookeeper客户端的API

	zkclient简化了节点的创建，用不同的方法创建不同的节点类型
	zkclient提供递归创建目录的功能
	zkclient提供递归删除目录的功能
	zkclient写数据直接write字符串(原生需要传byte[])
	zkclient读取直接返回Object对象(原生返回byte[])
	zkclient简化watch操作，无需反复注册watcher的问题
		- 监听节点数据的变化
		- 监听子节点的变化
	

## Curator - 提供更强大的功能
zookeeper的特性就是在分布式场景下的高可用，但是原生的API实现分布式功能比较麻烦，那么可以采用第三方的客户端来解决, Curator就是一个推荐的框架。

为了更好的实现java操作zookeeper，后来出现了curator框架，非常的强大，它是Apache的顶级项目，封装了更多丰富的操作。

例如，session超时重连，主从选举，分布式计数器，分布式锁等适合各种复杂分布式场景下的API封装。

Curator使用**链式编程**的风格，易读性更强，使用工厂方法创建连接对象。

1、使用CuratorFrameworkFactory的两个静态工厂方法来创建客户端。
	
	connectString	zk集群连接字符串，节点间用","分隔
	retryPolicy		重连策略
		ExponentialBackoffRetry
		BoundedExponentialBackoffRetry
		RetryNTimes
		RetryOneTime
		RetryUntilElapsed
	sessionTimeoutMs	会话超时时间，客户端多久未发送数据则会话session失效
	connectionTimeoutMs 连接超时时间，第一次与zk连接的超时时间


2、创建节点 create方法，可选链式项：

	create、creatingParentsIfNeeded、withMode、withACL、inBackground、forPath

3、删除节点 delete方法，可选链式项：

	delete、deletingChildrenIfNeeded、withVersion、withACL、inBackground、forPath

4、读取和修改数据

	getData、forPath 
	setData、forPath

5、异步回调 + 线程池处理返回结果

	比如创建一个节点时绑定一个回调函数，
	该回调函数可以输出服务器的状态码（判断操作是否成功）和事件的类型。
	回调函数中使用一个线程池进行优化操作：
	   场景---大批量操作N个节点时，通过线程池复用线程，线程池内部还提供缓冲队列，提高性能。

6、读取子节点

	getChildren、forPath

7、判断节点是否存在

	checkExists、forPath

8、watcher - 由curator-recipes提供

该依赖包使用NodeCache的方式在客户端实例中注册一个监听缓存，然后实现相关的监听方法即可。

设计思想：

没有采用重复设置watch的方式进行监控，这是与原生zk中显示设置watch的最大区别！

客户端维护一份节点信息的缓存，与zk服务器进行对比，将节点上发生的事件再通知给客户端。

	NodeCache - 负责节点的缓存
		NodeCacheListener	监听节点的新增、修改操作、（删除节点不触发事件）
			--如果关心节点删除事件，使用PathChildrenCache来实现。

	PathChildrenCache - 负责子节点的缓存
		PathChildrenCacheListener	监听子节点的新增、修改、删除操作

