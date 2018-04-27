package Caches;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.CountDownLatch;

import org.junit.Assert;
import org.junit.Test;

// https://objectcomputing.com/resources/publications/sett/june-2000-collaborating-with-the-java-memory-manager
public class Test04JDKReference {
	
	/** JAVA 中的几种引用类型介绍 
	 * 
	 * 1、为什么设计这几种引用类型？
	 * 		内存管理在Java中自动完成。 程序员不需要担心已经发布的引用对象。 这种方法的一个缺点是程序员无法知道何时收集特定的对象。 而且，程序员无法控制内存管理。 
	 * 		但是，java.lang.ref包定义了与垃圾收集器进行有限程度的交互的类。--- java 1.2版本
	 * 		 具体类SoftReference，WeakReference和PhantomReference是Reference的子类，它们以不同的方式与垃圾回收器交互。
	 * 2、各自的作用、应用场景？
	 * 		strong	普通的对象引用
	 * 		soft	常用于对内存敏感的缓存设计(在OutOfMemoryError前回收所有的soft引用所指向的内存)
	 * 		weak	常用于实现Canonicalized Mapping (one to one mapping)
	 * 		phantom	更安全的资源释放（pre-mortem cleanup），可用来代替finalize实现资源释放的功能-因为finalize释放资源存在时间上的不确定性，同时也可能降低gc的效率
	 * 
	 * 3、netty中使用了上述哪些类型的引用？
	 * 
In Java, order from strongest to weakest, there are: Strong, Soft, Weak and Phantom

	A Strong reference is a normal reference that protects the referred object from collection by GC. i.e. Never garbage collects.
	
	A Soft reference is eligible for collection by garbage collector, but probably won't be collected until its memory is needed. i.e. garbage collects before OutOfMemoryError.
	
	A Weak reference is a reference that does not protect a referenced object from collection by GC. i.e. garbage collects when no Strong or Soft refs.
	
	A Phantom reference is a reference to an object is phantomly referenced after it has been finalized, but before its allocated memory has been reclaimed.
		Phantom references are designed to allow for pre-mortem cleanup. 
		This means that you can learn about an object that is going to be garbage collected just before it actually gets collected and clean up resources it is using.

Analogy: 
	Assume a JVM is a kingdom, Object is a king of the kingdom, and GC is an attacker of the kingdom who tries to kill the king(object).

	When King is Strong, GC can not kill him.
	When King is Soft, GC attacks him but King rule the kingdom with protection until resource are available.
		(until resource are available 的解释：虽然king被保护，但是要attack him并不是不可能成功，但是需要一些额外的条件来辅助才行。
			---is eligible for collection by garbage collector, but probably won't be collected until its memory is needed for another use)
	When King is Weak, GC attacks him but rule the kingdom without protection.
	When king is Phantom, GC already killed him but king is available via his soul.
	 */
	
	
	/** weak 与 soft 的区别
	 * Difference between weak reference and soft reference：
	 * 		https://stackoverflow.com/questions/299659/whats-the-difference-between-softreference-and-weakreference-in-java
	 * 
	 * -----------------------------------------------------------
	 * A weak reference, simply put, is a reference that isn't strong enough to force an object to remain in memory. 
	 * Weak references allow you to leverage the garbage collector's ability to determine reachability for you, so you don't have to do it yourself. 
	 * 
	 * You create a weak reference like this:
	 * 		WeakReference weakWidget = new WeakReference(widget);
	 * and then elsewhere in the code you can use  weakWidget.get() to get the actual  Widget object. 
	 * 
	 * Of course the weak reference isn't strong enough to prevent garbage collection, 
	 * so you may find (if there are no strong references to the widget) that  weakWidget.get() suddenly starts returning null.
	 * 
	 * Weak Reference : 如果一个对象仅仅只被weak reference 所引用，没有其它类型的引用存在，则该对象就可以被gc回收。
	 * 
	 * -----------------------------------------------------------
	 * 
	 * All soft references to softly-reachable objects are guaranteed to have been cleared before the virtual machine throws an OutOfMemoryError. 
	 * 
	 * Soft reference allows for garbage collection, but begs the garbage collector to clear it only if there is no other option.
	 * 
	 * Soft reference : 当且仅当出现内存不够用，很大概率要出现 OutOfMemoryError错误之前，gc会回收掉这部分内存。
	 * 
	 * -----------------------------------------------------------
	 * The only real difference between a soft reference and a weak reference is that:
	 * 		the garbage collector uses algorithms to decide whether or not to reclaim a softly reachable object, but always reclaims a weakly reachable object.
	 * 		垃圾回收器使用某种算法来决定是否回收softly reachable object，对于weakly reference的对象，则总是进行回收的（前提：没有其它更强类型的引用存在）。		
	 * 
	 * The Sun JRE does treat SoftReferences differently from WeakReferences. 
	 * We attempt to hold on to object referenced by a SoftReference if there isn't pressure on the available memory. 
	 * One detail: 
	 * 	the policy for the "-client" and "-server" JRE's are different: 
	 * 		the -client JRE tries to keep your footprint small by preferring to clear SoftReferences rather than expand the heap, 
	 * 		whereas the -server JRE tries to keep your performance high by preferring to expand the heap (if possible) rather than clear SoftReferences.
	 */

	
	
	/**
	 * 强引用 - 不会被回收
	 */
	@Test
	public void testStrongReference() throws InterruptedException {
		HashMap<Employee, EmployeeVal> aMap = new HashMap<Employee, EmployeeVal>();

		Employee emp = new Employee("Vinoth"); // 1. strong reference
		EmployeeVal val = new EmployeeVal("Programmer");

		aMap.put(emp, val); // 2. strong reference

		emp = null; // 3. make strong reference = null

		while (aMap.size() > 0) {
			System.gc(); // 4. strong reference object can not be garbage collection
			System.out.println("Size of Map: " + aMap.size()); 
			Thread.sleep(1000);
		}
		// emp作为key，在aMap的entry中存在强引用，因此gc是不会清除这个引用的。
	}
	
	/**
	 * Soft reference - 内存不够时才会被回收 
	 * 	JVM进程的内存已经不够用了，在发生OutofMemoryError之前，jvm会回收所有的soft reference
	 * 
	 * Soft references are good for providing caches of objects that can be garbage collected when the Java Virtual Machine (JVM) is running low on memory.

In particular, the JVM guarantees a couple of useful things:

It will not garbage collect an object as long as there are normal, strong references to it.
It will not throw an OutOfMemoryError until it has "cleaned up" all softly reachable objects
 (objects not reachable by strong references but reachable through one or more soft references).
This allows us to use soft references to refer to objects that we could afford to have garbage collected, but that are convenient to have around until memory becomes tight.

Obviously, this means our program has to either be able to live without these objects or be able to recreate them. 
But we can let the garbage collector decide when to collect these objects, continuing the Java goal of letting the programmer avoid explicit memory management.
	 * 
	 * 
	 * -Xms20m -Xmx20m
	 */
	@Test
	public void testNotSoftReference() throws InterruptedException {
		List<Byte[]> list = new ArrayList<>();
		
		for(int i=0; i<4; i++) {
			Byte[] bytes = new Byte[1024*1024];
			list.add(bytes);
		}
		
		System.out.println(Runtime.getRuntime().maxMemory() / 1024 / 1024);
		System.out.println(Runtime.getRuntime().totalMemory() / 1024 / 1024);
		System.out.println(Runtime.getRuntime().freeMemory() / 1024 / 1024);
		
		Thread.sleep(5000);
		
		Byte[] bytes = new Byte[1024*1024];
		list.add(bytes);
		
	}
	
	@Test
	public void testUseSoftReference() throws InterruptedException {
		List<Reference<Byte[]>> list = new ArrayList<>();
		
		for(int i=0; i<8; i++) {
			Byte[] bytes = new Byte[1024*1024];
			list.add(new SoftReference<Byte[]>(bytes));
		}
		
		System.out.println(Runtime.getRuntime().maxMemory() / 1024 / 1024);
		System.out.println(Runtime.getRuntime().totalMemory() / 1024 / 1024);
		System.out.println(Runtime.getRuntime().freeMemory() / 1024 / 1024);
		
		Thread.sleep(5000);
		
		Byte[] bytes = new Byte[1024*1024];
		list.add(new SoftReference<Byte[]>(bytes));
		
	}

	/**
	 * Weak Reference 
	 * 是否被收回，与可用内存大小无关。- 如果没有strong, soft引用，仅有weak reference，则被weak 引用的对象就满足被垃圾回收的条件，也就会被gc清除。
	 * 
	 * For example, imagine we have permanent storage (a file, database, etc.) containing a large number of Employee objects, 
	 * and we only work with a subset of them at any given time 
	 * (perhaps there are too many to fit in a running JVM on the machines we have, and we only need to work with a small number of them at any given time).
	 */
	@Test
	public void testWeakHashMap() throws InterruptedException {
		Map<String, Integer> hashMap = new HashMap<>();
		Map<String, Integer> weakHashMap = new WeakHashMap<>();
		for(int i=0; i<100; i++) {
			hashMap.put(UUID.randomUUID().toString(), i);
			weakHashMap.put(UUID.randomUUID().toString(), i);
		}
		
		System.gc();
		Thread.sleep(1000);
		
		System.out.println(hashMap.size());
		System.out.println(weakHashMap.size());
	}
	
	@Test
	public void testWeakReference() throws InterruptedException {
		WeakHashMap<Employee, EmployeeVal> aMap = new WeakHashMap<Employee, EmployeeVal>();
		
		CountDownLatch latch  = new CountDownLatch(1);
		
		Employee emp = new Employee("Vinoth"); // 1. strong reference
		emp.setLatch(latch);
		EmployeeVal val = new EmployeeVal("Programmer");

		aMap.put(emp, val); // 2. weak reference

		emp = null; // 3. make strong reference = null

		System.gc();
		int count = 0;
		while (0 != aMap.size()) {
			++count;
			System.gc(); // 4. weak reference object will be garbage collection
		}
		System.out.println(Thread.currentThread().getName() + ": Took " + count + " calls to System.gc() to result in weakHashMap size of : " + aMap.size());
		// If an object has only weak reference with other objects, then its ready for garbage collection.
		
		latch.await();
	}
	
	/**
	 * A phantom reference is used to determine when an object is just about to be reclaimed.
	 * @throws InterruptedException 
	 */
	@Test
	public void testPhantomReference1() throws InterruptedException {
		ReferenceQueue<Object> rq = new ReferenceQueue<>();
		Object object = new Object();
		// Creates a new phantom reference that refers to the given object and is registered with the given queue. 
	    PhantomReference<Object> pr = new PhantomReference<Object>(object, rq);

	    int n = 0;
	    
	    while (true) {
	      Reference<?> r = rq.remove(1000);
	      if (r == pr) {
	        // about to be reclaimed.
	    	System.out.println(pr.get()); // always return null. the referent of a phantom reference is always inaccessible, this method always returns null.
	        r.clear();
	        break;
	      }
	      if(++n % 10 == 0) {
	    	  object = null; // cancel strong reference
	      }
	      System.out.println("gc: " + n);
	      System.gc();
	    }
	}
	
	/**
	 * PhantomReference + ReferenceQueue ，
	 * 	 对象已经被finalized，在真正回收内存之前，将PhantomReference放入到ReferenceQueue。通过观察ReferenceQueue的变化，从而得知对象是否被垃圾清理了。如果是，进行一些自定义操作。
	 * 
	 * A PhantomReference must be used with the ReferenceQueue class. 
	 * 
	 * The ReferenceQueue is required because it serves as the mechanism of notification. 
	 * 
	 * When the garbage collector determines an object is phantomly reachable, the PhantomReference object is placed on its ReferenceQueue. 
	 * 
	 * The placing of the PhantomReference object on the ReferenceQueue is your notification that the object the PhantomReference object referred to has been finalized and is ready to be collected. 
	 * 
	 * This allows you to take action just prior to the object memory being reclaimed.
	 * 
	 */
	@Test
	public void testPhantomReference2() throws InterruptedException {
		// 引用队列，在检测到"适当的可访问性"更改后（没有strong,soft,weak引用），垃圾收集器会将已注册的引用对象附加到该引用队列。
		ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
		
		Object object = new Object() {
			public String toString() {
				return "Referenced Object";
			}
			@Override
			protected void finalize() throws Throwable {
				System.out.println(Thread.currentThread().getName() + "---------> Execute finalize on My Object");
			}
		};

		System.out.println("Testing PhantomReference.");
		Reference<Object> myObjReference = new PhantomReference<Object>(object, referenceQueue);

		System.out.println("reference enqueued: " + myObjReference.isEnqueued());

		System.gc();
		System.out.println("reference enqueued: " + myObjReference.isEnqueued());

		object = null;
		
		Assert.assertNull(referenceQueue.remove(1000));
		
		// if and only if this reference object has been enqueued (either by the program or by the garbage collector. )
		while(! myObjReference.isEnqueued()) {
			System.gc();
			System.out.println("reference enqueued: " + myObjReference.isEnqueued());
		}
		
		System.out.println("reference enqueued: " + myObjReference.isEnqueued());
		
		// remove是阻塞式方法，当返回结果时，表明对象被垃圾回收了。
		Reference<?> retrieved = referenceQueue.remove(); // Removes the next reference object in this queue, blocking until one becomes available.
		if(retrieved == myObjReference) {
			System.out.println("对象被垃圾回收了");
			System.out.println(retrieved.toString());
		}
	}
	
	class Employee {
		String name;
		CountDownLatch latch;

		public Employee(String name) {
			this.name = name;
		}

		public void setLatch(CountDownLatch latch) {
			this.latch = latch;
		}

		@Override
		protected void finalize() throws Throwable {
			System.out.printf("[%s] Employee finalized start\n", Thread.currentThread().getName());
			Thread.sleep(5000); // If the finalize method runs for a long time, it can delay execution of finalize methods of other objects.
			latch.countDown();
			System.out.printf("[%s] Employee finalized over\n", Thread.currentThread().getName());
		}
		
		
	}

	class EmployeeVal {
		String value;

		public EmployeeVal(String value) {
			this.value = value;
		}
	}
}
