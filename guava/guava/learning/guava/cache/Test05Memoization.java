package guava.cache;

import java.math.BigInteger;
import java.time.LocalTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Memoization features - 对方法调用具有记忆功能
 *		
 * 	Memoization is a technique that avoids repeated execution of a computationally expensive function by caching the result of the first execution of the function.
 * 
 * Memoization vs. Caching
 * 	Caching is a more generic term that addresses the problem at the level of class instantiation, object retrieval, or content retrieval
 * 	Memoization solves the problem at the level of method/function execution.
 * 
 * Memoization applies to functions with no argument (Supplier) and functions with exactly one argument (Function). 
 * 	
 * We can call memoization APIs on-demand and specify an eviction policy which controls the number of entries held in memory 
 * 	and prevents the uncontrolled growth of memory in use by evicting/removing an entry from the cache once it matches the condition of the policy.
 * 
 * Memoization makes use of the Guava Cache.
 * 
 */
public class Test05Memoization {
	/**
	 * Supplier Memoization
	 * 	There are two methods in the Suppliers class that enable memoization: 
	 * 		memoize, and memoizeWithExpiration.
	 */
	
	@Test
	public void testSupplierMemoize() throws InterruptedException {
		Supplier<Long> memoizedSupplier = Suppliers.memoize(CostlySupplier::generateBigNumber);
		
		for(int i=0; i<6; i++) {
			System.out.println(memoizedSupplier.get());
			TimeUnit.SECONDS.sleep(1);
		}
	}
	
	@Test
	public void testSupplierMemoizeWithExpiration() throws InterruptedException {
		Supplier<Long> memoizedSupplier = Suppliers.memoizeWithExpiration(
				CostlySupplier::generateBigNumber, 5, TimeUnit.SECONDS);
		
		for(int i=0; i<6; i++) {
			System.out.println(memoizedSupplier.get());
			TimeUnit.SECONDS.sleep(1);
		}
	}
	
	public static class CostlySupplier {
	    private static Long generateBigNumber() {
	        try {
	        	System.out.println("costly supplier run...");
	            TimeUnit.SECONDS.sleep(2);
	        } catch (InterruptedException e) {}
	        return System.currentTimeMillis();
	    }
	}
	
	
	/**
	 * Function Memoization
	 * 	Note: Guava doesn’t support memoization of functions with more than one argument.
	 * 	
	 * 多个参数怎么办？
	 * 		function(a,b,c) -> function(x)  x => a:b:c 
	 */
	@Test
	public void testFunctionMemoization() throws ExecutionException {
		FibonacciSequence seq = new FibonacciSequence();
		
		System.out.println(LocalTime.now().toString() + " first call(2), value= " + seq.get(3));
		System.out.println(LocalTime.now().toString() + " second call(2), value= " +seq.get(3));
		System.out.println(LocalTime.now().toString() + " third call(2), value= " +seq.get(3));
		
		System.out.println();
		
		System.out.println(LocalTime.now().toString() + " first call(3), value= " + seq.get(5));
		System.out.println(LocalTime.now().toString() + " second call(3), value= " +seq.get(5));
		System.out.println(LocalTime.now().toString() + " third call(3), value= " +seq.get(5));
		
		System.out.println();
		
		System.out.println(LocalTime.now().toString() + " first call(2), value= " + seq.get(3));
		System.out.println(LocalTime.now().toString() + " second call(2), value= " +seq.get(3));
		System.out.println(LocalTime.now().toString() + " third call(2), value= " +seq.get(3));
		
	}
	
	// CacheLoader 执行function，将计算结果填充到LoadingCache中
	private class FibonacciSequence {
		// key : LoadingCache‘s key is the Function‘s argument/input
		// value : while the map’s value is the Function‘s returned value
		
	    private  LoadingCache<Integer, BigInteger> memo = CacheBuilder.newBuilder()
	      .maximumSize(1) 	// remove the oldest entry once the memo size has reached N entries
//	      .expireAfterAccess(2, TimeUnit.SECONDS)
	      .build(CacheLoader.from(this::getFibonacciNumber));
	 
	    private BigInteger getFibonacciNumber(int n) {
	    	try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        if (n == 0) {
	            return BigInteger.ZERO;
	        } else if (n == 1) {
	            return BigInteger.ONE;
	        } else {
	        	// getUnchecked method which returns the value if exists without throwing a checked exception.
	            return memo.getUnchecked(n - 1).add(memo.getUnchecked(n - 2));
	        }
	    }
	    
	    public BigInteger get(int n) throws ExecutionException {
			return memo.get(n);
	    }
	}
	
	
	
}
