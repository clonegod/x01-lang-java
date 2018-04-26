## zookeeper数据模型
![Image of zknamespace](https://github.com/clonegod/tools/blob/master/images/zknamespace.jpg)

### 
### 1、 名称空间
每个子目录项如NameService都被称为znode，这个znode是它所在路径的唯一标识。

比如，Server1这个znode的标识为/NameService/Server1

### 2、多级目录
znode节点可以有子节点目录，并且每个znode可以存储数据。

注意，EPHEMERAL类型的目录节点不创建子节点目录。

### 3、多版本
znode是由版本的，每个znode中存储的数据可以由多个历史版本，

也就是说同一个访问路径中可以存储多份数据。

### 4、持久节点、临时节点
znode节点默认是持久化存储的，除非显示删除，否则一直存在。

znode节点可以是临时节点，一旦创建这个znode节点的客户端与服务器失去联系，这个znode节点将自动删除。

zookeeper的客户端和服务器通信采用长连接的方式，每个客户端和服务器通过心跳来保持连接，这个连接状态成为session。

如果znode是临时节点，这个session失效，znode也就随之被删除。

### 5、自动编号

znode目录名可以自动编号，如App1已经存在，再创建的话，新节点将自动命名为App2。

### 6、监控watch

znode可以被监控，监控的对象包括这个目录节点中存储的数据修改，以及子节点目录的变化等。

一旦被监控的节点发生修改，就可以通知设置了监控的客户端，这个是zookeeper的核心特性。

zookeeper的很多功能都是基于watch机制来实现的。