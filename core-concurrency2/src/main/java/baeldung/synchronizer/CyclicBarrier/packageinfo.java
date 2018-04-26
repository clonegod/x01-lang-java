package baeldung.synchronizer.CyclicBarrier;

/**
 * CyclicBarriers 
 * 	CyclicBarriers are used in programs in which we have a fixed number of threads 
 * 	that must wait for each other to reach a common point before continuing execution.
 * 	1. 需要在构造的时候指定线程数；
 * 	2. CyclicBarriers可重复使用，通过reset()恢复到初始状态；
 *  3. 可指定一个Runnable任务，由最后一个到达barrier的线程执行；
 *  4. 线程间必须互相等待，直到所有线程都到达“目标地点”，再继续执行。
 */
