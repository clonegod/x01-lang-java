package baeldung.copyonwrite.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList 
 * This is a very useful construct in the multi-threaded programs 
 * 	– when we want to iterate over a list in a thread-safe way without an explicit synchronization.
 * 	不显示加锁的情况下，以线程安全的方式对集合进行迭代
 * 
 * 底层机制：
 * When we are using any of the modify methods – such as add() or remove() 
 * 	– the whole content of the CopyOnWriteArrayList is copied into the new internal copy.
 * 	一旦发生添加/删除操作对集合进行修改，内部将自动复制出一个不可变的快照副本。
 * 	
 * 因此在副本上进行迭代时，即使发生并发修改也不会影响到当前迭代。
 * 新的修改将在下一次获取的迭代器上体现。当前迭代器是看不到新增修改的。
 * 
 * 使用场景：
 * 	需要经常对集合进行迭代，但是很少发生修改操作（add,remove）.
 * 
 * 如果添加元素是一个常见的操作，那么不建议使用CopyOnWriteArrayList - 因为频繁的副本复制会影响性能。
 * */
