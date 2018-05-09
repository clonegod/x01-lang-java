package baeldung.synchronizer.Semaphore;

/**
 * Semaphore
 * 	We can use semaphores to limit the number of concurrent threads accessing a specific resource.
 * 	
 * 	使用Semaphore限制并发多线程对共享资源的访问
 * 1. 信号量可设置为0个，由其它线程在某个时机通过release()设置可用信号；
 * 2. 信号量可设置为1个，实现互斥访问synchronized的效果；
 * 3. 信号量可设置为n个
 */
