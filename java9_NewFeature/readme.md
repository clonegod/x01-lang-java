# Java9 新特性

## Reactive Streams 前时代

### 时代局限性（java9之前）
	阻塞编程
		无法并行计算
		资源低效使用
	异步编程
		Callback
		Future
		
	java1.4 NIO 
		- Non-Blocking I/O 同步非阻塞
		- 基于Reactor模式
		- https://en.wikipedia.org/wiki/Reactor_pattern
		- 优点：非阻塞（基于事件通知进行处理：连接事件、读事件、写事件等）
		- 缺点：同步（数据从内核复制到用户空间的过程中，线程需要同步等待）
		- Proactor pattern (a pattern that also demultiplexes and dispatches events, but asynchronously)

--------------


## Reactive Streams Java9
	
	WebFlux 依赖 -> Reactor 依赖 -> Reactive Streams API 
	
	http://projectreactor.io/docs/core/release/reference/
	https://github.com/reactive-streams/reactive-streams-jvm
	 
### Reactive 编程模式
	观察者模式 + 迭代器模式  + 责任链模式 的综合体
	
	 核心接口
		Mono	异步  0-1   元素序列: Future<Optional<?>>
		Flux	异步  0-N 元素序列: Future<Collection<?>>

	编程方式
		接口编程
		函数式编程（Lambda）
	
	信号
		onSubscribe()	订阅事件
		onNext()			数据达到事件
		onComplete()		订阅完成事件
		onError()			订阅异常
		request()			请求
		cancel()			取消
		
	
### Reactive Streams API/规范的具体实现
	- Java 9 Flow API 
	- RxJava: Reactive Extension Java
	- Reactor: Reactor Framework 

		


