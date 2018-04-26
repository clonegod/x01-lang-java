## Watcher、ZK状态、事件类型

zookeeper有watch事件，是1次性触发的。当watch监视的znode发生变化时，zookeeper就会通知设置了该watch的client。

### 支持watch的API
	zk.exists(path, watch)			判断path是否存在，并监控path对应的znode
	zk.getData(path, watch, stat)	获取path对应znode的value，并监控path对应的znode
	zk.getChildren(path, watch)		获取path对应znode的子节点，并监控path下的子节点变化
客户端调用相关api查询znode信息，zookeeper返回结果给客户端。

如果客户端需要在节点发生任何变化时都得到通知，那就可以设置watch参数为true，

这样，当对应的节点发生变化时，客户端将立即得到通知，然后在处理通知的方法中进行细节处理。


注意：**watch是1次性的**。如果需要一直监控节点，则每次调用api都要设置watch=true.

客户端通过不同的事件类型和状态类型来判断znode上发生了哪种变化。
### 事件类型（单个znode节点上可能发生的事件类型）
	EventType.None	客户端与zk集群连接成功事件
	EventType.NodeCreated	节点创建事件
	EventType.NodeDataChanged	节点的数据发生修改事件
	EventType.NodeChildrenChanged	节点的子节点发生修改事件
	EventType.NodeDeleted	节点删除事件

### 状态类型（客户端与zk集群连接相关）
	KeeperState.SyncConnected	连接成功
	KeeperState.Disconnected	连接断开
	KeeperState.AuthFailed		认证失败
	KeeperState.Expired			连接过期

	