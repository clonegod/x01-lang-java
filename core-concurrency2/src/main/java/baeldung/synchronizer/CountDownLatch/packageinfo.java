package baeldung.synchronizer.CountDownLatch;

/**
 * CountDownLatch 
 * 		block a thread until other threads have finished some processing.
 * 
 * 1. 需要在构造的时候指定线程数；
 * 2. CountDownLatch不可重用；
 * 3. 一个线程await()，等待其它线程countDown()
 */
