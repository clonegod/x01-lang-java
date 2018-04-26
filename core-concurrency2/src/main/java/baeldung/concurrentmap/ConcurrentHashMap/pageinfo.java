package baeldung.concurrentmap.ConcurrentHashMap;

/**
 * ConcurrentMap
 * 	solving the problem of reconciling throughput with thread-safety.
 * 	解决吞吐量和线程安全性的问题。
 * 	
 * 	通过覆盖多个接口默认方法，ConcurrentHashMap提供了线程安全和内存一致性原子操作。
 * 
 * 

In a multi-threading environment, where multiple threads are expected to access a common Map, 
the ConcurrentHashMap is clearly preferable.
在多线程并发环境下操作同一个Map，使用ConcurrentHashMap是最优的选择，既可以保证线程安全，又提供了最高的运行速度。

However, when the Map is only accessible to a single thread, 
HashMap can be a better choice for its simplicity and solid performance.
如果Map只被一个线程所访问，使用HashMap更简单，性能更好。


ConcurrentHashMap Pitfalls-使用陷阱
1. 大部分方法不允许null key or null value， 但是compute*和merge允许value为null；
2. size, isEmpty, and containsValue等聚合操作在1个线程中是可靠的，但是如果有其它线程在并发修改map，则结果是不可靠的；
3. hashCode 应该最大可能的保持不同，否则会严重影响map的性能---相同hashcode的元素在底层变为一个链表结构
4. iterator迭代操作只能在单线程环境下使用，如果在多线程环境下进行迭代，可能结果是错误的---不会抛出ConcurrentModificationException
5. 默认的容量是16个table，不过可以通过concurrencyLevel来指定并发线程数，以便在构造时确定内部的table个数
6. 使用remapping修改key-value映射关系的操作，应该尽可能快的执行完成，避免产生意外的阻塞
7. key可以不具有排序性，但如果需要map的key具有排序功能，使用ConcurrentSkipListMap-a concurrent version of TreeMap 

===================================================================
jdk1.8新增的接口默认方法
	getOrDefault
	forEach
	replaceAll
	computeIfAbsent
	computeIfPresent
	compute
	merge

jdk1.8之前存在的方法
	putIfAbsent
	remove
	replace(key, oldValue, newValue)
	replace(key, value)
 */
