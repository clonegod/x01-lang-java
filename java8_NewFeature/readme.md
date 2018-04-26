# Java8 新特性学习

## 1、lambada表达式、函数式编程



## 2、集合框架的流式处理 - stream / parallel stream




## 3、 新的日期时间API
	LocalDate
	LocalTime
	LocalDateTime
	Instant


## 4、并行编程/异步编程

### Java 5之前的时代
	并发实现
		Java Green Thread
		Java Native Thread

	编程模式
		Thread
		Runnable
	
	存在的局限性
		缺少线程(池)管理的原生支持
		缺少“锁”API -- 仅支持synchronized修饰符来控制线程同步
		缺少执行完成状态的原生支持
		执行结果获取困难 
		Double Check Locking 不确定性 - 单例模式中使用synchronized存在DCL问题



### Java 5 时代
	并发框架
		J.U.C = java.util.concurrent
	
	编程模型
		Executor, ThreadPollExecutor
		Runnable, Callable
		Future
		FutureTask


### Java 7 时代
	并发框架
		Fork/Join

	编程模型
		ForkJoinPool
		ForkJoinTask
		RecursiveAction

	存在的限制
		阻塞式返回结果
		无法链式多个Future
		无法合并多个Future结果
		缺少异常处理	


### Java 8 时代
	异步并行框架 
		Fork/Join
	
	编程模型
		CompletionStage
		CompletableFuture