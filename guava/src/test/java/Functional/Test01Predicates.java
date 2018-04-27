package Functional;

import java.util.List;

import org.junit.Test;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

public class Test01Predicates {
	
	/**
	 * Predicate用来声明某个过滤条件，当被筛查的元素不满足条件时，将被过滤掉。
	 */
	@Test
	public void testFilterCollection() {
		Predicate<String> startWithX = 
			new Predicate<String>(){
				@Override
				public boolean apply(String paramT) {
					return paramT.contains("x");
				}
			};
		
		Predicate<String> endWithX = 
			new Predicate<String>(){
				@Override
				public boolean apply(String paramT) {
					return paramT.endsWith("x");
				}
			};
		
		// 组合
		Predicate<String> composite = Predicates.and(startWithX, endWithX);
		
		List<String> list = Lists.newArrayList("x","y","x1x","x2");
		
		FluentIterable<String> fiter = FluentIterable.from(list).filter(composite);
		
		System.out.println(fiter.toList());
	}
	
	@Test
	public void testFilterCollection2() {
		// 组合
		Predicate<String> composite = Predicates.and(
											(String x) -> x.startsWith("x"), 
											(String y) -> y.endsWith("x")
										);
		
		List<String> list = Lists.newArrayList("x","y","x1x","x2");
		
		FluentIterable<String> fiter = FluentIterable.from(list).filter(composite);
		
		System.out.println(fiter.toList());
	}
	
}
