package demo.guava.functional.supplier;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

public class SuppliersTest {
	
	/**
	 * Supplier wrapped
	 * 对象只初始化一次
	 */
	@Test
	public void testSupplierMemoize() throws InterruptedException {
		Supplier<Integer> memoize = Suppliers.memoize(new Supplier<Integer>() {
			@Override
			public Integer get() {
				System.out.println("init supplier wrapped object");
				return ThreadLocalRandom.current().nextInt(100);
			}
		});
		
		System.out.println(memoize.get());
		
		TimeUnit.SECONDS.sleep(5);
		
		System.out.println(memoize.get());
	}
	
	/**
	 * memoizeWithExpiration函数
	 * 创建过期设置的Supplier对象，时间过期，get对象会重新初始化对象
	 */
	@Test
	public void testSupplierMemoizeWithExpiration() throws InterruptedException {
		Supplier<Integer> memoize = Suppliers.memoizeWithExpiration(new Supplier<Integer>() {
			@Override
			public Integer get() {
				System.out.println("init supplier wrapped object");
				return ThreadLocalRandom.current().nextInt(100);
			}
		}, 3, TimeUnit.SECONDS);
		
		System.out.println(memoize.get());
		
		// 超时后，会重新调用get()
		TimeUnit.SECONDS.sleep(5);
		
		System.out.println(memoize.get());
	}
	
}
