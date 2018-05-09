package baeldung.executor.threadpool.guava;

/**


==============================================================
Thread Pool’s Implementation in Guava 
- Guava对Java线程池的扩展

1. MoreExecutors helper class 是唯一的入口，通过静态工厂方法创建需要的线程池；
2. Direct Executor Service	     提交到线程池的任务由当前线程来执行；
3. Exiting Executor Services	通过对已有的线程池进行功能增强来提供新的功能
4. Listening Decorators		异步任务结果的再次封装---监听器
 */
