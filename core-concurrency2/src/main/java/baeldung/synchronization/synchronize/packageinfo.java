package baeldung.synchronization.synchronize;

/**
 * synchronized 
 * 	多线程环境下，两个以上的线程尝试同时更新/修改同一个共享资源时，会发生资源竞争。
 * 	Java通过线程同步机制来避免对共享数据的不安全操作。
 * 
 * 多线程使用锁进行资源保护，必须使用同一个锁，否则加锁策略无效。
 * 
使用同步块时，内部Java使用一个称为监视器锁或内部锁的监视器来提供同步。
监视器被绑定到一个对象上，同一对象的所有同步块只有一个线程同时执行。

The synchronized keyword can be used on different levels:
	Instance methods - 实例方法
	Static methods	- 静态方法
	Code blocks		- 代码块
 */
