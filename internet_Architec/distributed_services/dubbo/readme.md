## Dubbo 开源分布式服务框架、SOA基础框架

使用指南：[dubbo-user-book/](http://dubbo.io/books/dubbo-user-book/)

### a high-performance, java based, open source RPC framework
![dubbo-architecture](https://github.com/clonegod/tools/blob/master/images/dubbo-architecture.png)

## 服务组件
### Registry 注册中心
	- 提供服务注册，可基于zookeeper等分布式系统
### Service Provider
	- 对外暴露服务
### Service Consumer
	- 服务调用方
### Admin 
	- 运维管理与配置


---------------------------------------------------------


Dubbo是一个分布式服务框架，致力于提供高性能和透明化的RPC远程服务调用方案，以及SOA服务治理。

简单的说，Dubbo就是一个远程服务调用的分布式框架。

### 核心功能：
**1、远程通信 interface based remote call**

提供对多种基于长连接的NIO框架的抽象，包括多种线程模型、序列化、以及“请求-响应”模式的信息交换方式。

**2、集群容错和负载均衡 fault tolerance & load balancing**

提供基于接口方法的透明远程过程调用，包括多种协议的支持、软负载均衡、失败容错、地址路由(配置服务对哪些客户端可见)、动态配置等集群支持。

**3、自动服务注册与发现 automatic service registration & discovery**

基于注册中心提供的目录服务，使得服务的调用方可以动态的查找服务提供方，使地址透明化，使服务提供方可以平滑增加或减少机器。


### Dubbo的特性
1、透明化的远程方法调用，对调用方而言，就像调用本地方法一样，只需要简单配置，没有任何API侵入。

2、软负载均衡以及容错机制，可在内网替代F5等硬件负载均衡器，降低成本。

3、服务主动注册与发现，不再需要硬编码服务方的地址，注册中心基于接口名查询服务提供者的IP地址，并且能够平滑删除或新增服务提供者。

4、Dubbo采用全Spring配置方式，透明化接入应用，对应用没有任何API侵入，只需要Spring加载Dubbo配置即可，Dubbo基于Spring的schema扩展进行加载。





