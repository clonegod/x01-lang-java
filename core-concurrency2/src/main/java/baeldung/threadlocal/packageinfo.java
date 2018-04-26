package baeldung.threadlocal;

/**
 * ThreadLocal 
 * 	store data individually for the current thread – and simply wrap it within a special type of object.
 * 	The TheadLocal construct allows us to store data that will be accessible only by a specific thread.
 * 	
 * 	ThreadLocal stores data inside of a map – with the thread as the key.
 * 
 * 	
 * 	ThreadLocal<Integer> threadLocalValue = new ThreadLocal<>();
 * 
 * 	ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 1); // 设置初始值
 * 	
 * 	threadLocal.set(x); // 设置绑定对象
 * 	threadLocal.get();	// 获取绑定对象
 * 	threadLocal.remove(); // 删除绑定对象
 */
