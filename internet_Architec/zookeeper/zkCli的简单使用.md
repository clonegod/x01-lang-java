# zkCli的使用

进入zookeeper命令行客户端

	/usr/local/zookeeper/bin/zkCli.sh

操作命令

	stat path [watch]				查看path的状态，可监控
    set path data [version]			设置数据到path
    ls path [watch]					列出子目录，可监控
    delquota [-n|-b] path
    ls2 path [watch]				查看状态并列出子目录，可监控
    setAcl path acl
    setquota -n|-b val path
    history 						列出历史命令
    redo cmdno						重新执行某个历史命令，指定命令编号执行
    printwatches on|off
    delete path [version] 			删除子目录
    sync path
    listquota path
    rmr path 						递归删除
    get path [watch]				获取存储在path上的数据
    create [-s] [-e] path data acl  创建目录
    addauth scheme auth
    quit 							退出客户端
    getAcl path
    close 
    connect host:port

查看zookeeper上存储了哪些数据

	[zk: localhost:2181(CONNECTED) 16] ls /
	[zookeeper]

创建节点 /test

	 [zk: localhost:2181(CONNECTED) 19] create /test "my test"
	 Created /test

查询节点数据 /test

	[zk: localhost:2181(CONNECTED) 20] get /test
	my test
	cZxid = 0x300000008
	ctime = Mon Jan 01 12:41:21 CST 2018
	mZxid = 0x300000008
	mtime = Mon Jan 01 12:41:21 CST 2018
	pZxid = 0x300000008
	cversion = 0
	dataVersion = 0
	aclVersion = 0
	ephemeralOwner = 0x0
	dataLength = 7
	numChildren = 0

创建子节点 /test/app1

	[zk: localhost:2181(CONNECTED) 21] create /test/app1 "this is app1"   
	Created /test/app1

获取子目录的数据 /test/app1
	
	[zk: localhost:2181(CONNECTED) 22] get /test/app1
	this is app1
	cZxid = 0x300000009
	ctime = Mon Jan 01 12:42:56 CST 2018
	mZxid = 0x300000009
	mtime = Mon Jan 01 12:42:56 CST 2018
	pZxid = 0x300000009
	cversion = 0
	dataVersion = 0
	aclVersion = 0
	ephemeralOwner = 0x0
	dataLength = 12
	numChildren = 0


递归删除整个目录 /test/app1, /test

	[zk: localhost:2181(CONNECTED) 25] rmr /test
	[zk: localhost:2181(CONNECTED) 26] ls /
	[zookeeper]	

退出客户端 quit

	[zk: localhost:2181(CONNECTED) 37] quit
	Quitting...