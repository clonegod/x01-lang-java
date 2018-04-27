package guava.functional.tests;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;

/**
 * Functional Utilities
 * 函数式编程的学习建议 - 尝试去使用它，在集合的处理中应用较多，尤其是java8中的stream API。
 * 
 * 注意：
 * 	Java 8 includes the java.util.function and java.util.stream packages, which supercede Guava's functional programming classes for projects at that language level.
 * 	Java 8 提供了函数式编程的功能，从语言层面对函数式编程提供了支持。
 * 	在Java8之前，你可以使用Guava的函数式工具。从Java8开始，直接使用Java8就可以函数式编程了。
 * 
 * Guava provides two basic "functional" interfaces
 * 	1、Function<A, B>  可以理解为一个方法/算法的声明，之后将这个算法传入到其它方法中去运算得到结果。  A是入参，B是返回值，B apply(A input).
 * 		
 *	2、Predicate<T>	可以理解为一个断言，用于判断元素是否满足某个条件，常用于过滤集合中的元素。返回值为bool类型，boolean apply(T input) . 
 *		The most basic use of predicates is to filter collections. All Guava filter methods return views.
 *		Collection type：
 *			Iterable
 *		Filter methods：
 *			Iterables.filter(Iterable, Predicate)
 *			FluentIterable.filter(Predicate)
 */
public class Test02Functional {

	List<String> strings = Lists.newArrayList("A", "b", "B", "AB", "Bc", "ABC", "Abc", "abc", "CCC", "AAA");
	
	@Test
	public void testCapStringLen_1() {
		// 计算字符串长度的算法
		Function<String, Integer> lengthFunction = new Function<String, Integer>() {
			  public Integer apply(String string) {
			    return string.length();
			  }
			};
		
		// 集合中元素的过滤规则
		Predicate<String> allCaps = new Predicate<String>() {
			  public boolean apply(String string) {
			    return CharMatcher.javaUpperCase().matchesAllOf(string);
			  }
			};
			
		// 1、 找出所有大写的字符串  2、计算每个字符串的长度	
		Iterable<String> fromIterable = Iterables.filter(strings, allCaps);
		
		Iterable<? extends Integer> elements = Iterables.transform(fromIterable, lengthFunction);
			
		Multiset<Integer> lengths = HashMultiset.create(elements);
		
		System.out.println(lengths);
	}
	
	
	@Test
	public void testCapStringLen_2() {
		Multiset<Integer> lengths = HashMultiset.create(
			  FluentIterable.from(strings)
			    .filter(new Predicate<String>() {
			       public boolean apply(String string) {
			         return CharMatcher.javaUpperCase().matchesAllOf(string);
			       }
			     })
			    .transform(new Function<String, Integer>() {
			       public Integer apply(String string) {
			         return string.length();
			       }
			     }));
		System.out.println(lengths);
	}
	
	/**
	 * 第1，2种使用函数式来完成功能，没有第3种好。第1，2种写法代码多，可读性也不好。
	 * 这个例子要说明是：
	 * 常规的编程并不会被取代，函数式编程也不哪里都适用，根据实际情况选择最合适的技术。
	 * 引用一句别人的话：技术没有对错，只有适不适合。
	 */
	@Test
	public void testCapStringLen_3() {
		Multiset<Integer> lengths = HashMultiset.create();
		for (String string : strings) {
		  if (CharMatcher.javaUpperCase().matchesAllOf(string)) {
		    lengths.add(string.length());
		  }
		}
		System.out.println(lengths);
	}
	
	@Test
	public void testCapStringLen_4() {
		Multiset<Integer> lengths = HashMultiset.create();
		strings.stream()
				.filter(item -> CharMatcher.javaUpperCase().matchesAllOf(item) )
				.forEach(item -> lengths.add(item.length()));
		System.out.println(lengths);
	}
	
	// ---------------------------------------------------------------------------//
	
	@Test
	public void test01() {
		List<Integer> numbers = Lists.newArrayList(1, 2, 3);
		
		// 这个转化逻辑可以用function来实现
		List<String> strings = Lists.newArrayList();
		for(Integer i : numbers) {
			strings.add(String.valueOf(i));
		}

		assertThat(strings, contains("1", "2", "3"));
	}
	
	
	@Test
	public void test02() {
		List<Integer> numbers = Lists.newArrayList(1, 2, 3);
		List<String> asStrings = Lists.transform(numbers, Functions.toStringFunction());
		assertThat(asStrings, contains("1", "2", "3"));
	}
	
	/**
	 * filter a collection by a condition (custom Predicate)
	 */
	@Test
	public void test03() {
		List<Integer> numbers = Lists.newArrayList(1, 2, 3, 6, 10, 34, 57, 89);
		Predicate<Integer> acceptEven = new Predicate<Integer>() {
		    @Override
		    public boolean apply(Integer number) {
		        return (number % 2) == 0;
		    }
		};
		
		// Collections2 - Java 8 users: several common uses for this class are now more comprehensively addressed by the new Stream library.
		
		// 筛选偶数 ，二分查找
		List<Integer> evenNumbers = Lists.newArrayList(Collections2.filter(numbers, acceptEven));
		Integer found = Collections.binarySearch(evenNumbers, 10);
		assertThat(found, greaterThan(0));
	}
	
	/**
	 * filter out nulls from a collection
	 */
	@Test
	public void test04() {
		List<String> withNulls = Lists.newArrayList("a", "bc", null, "def");
		Iterable<String> withoutNuls = Iterables.filter(withNulls, Predicates.notNull());
		assertTrue(Iterables.all(withoutNuls, Predicates.notNull()));
		System.out.println(Iterables.toString(withoutNuls));
	}
	
	
	/**
	 * check condition for all elements of a collection
	 */
	@Test
	public void test05() {
		List<Integer> evenNumbers = Lists.newArrayList(2, 6, 8, 10, 34, 90);
		Predicate<Integer> acceptEven = new Predicate<Integer>() {
		    @Override
		    public boolean apply(Integer number) {
		        return (number % 2) == 0;
		    }
		};
		assertTrue(Iterables.all(evenNumbers, acceptEven));
	}
	
	/**
	 * negate a predicate
	 */
	@Test
	public void test06() {
		List<Integer> evenNumbers = Lists.newArrayList(2, 6, 8, 10, 34, 90);
		Predicate<Integer> acceptOdd = new Predicate<Integer>() {
		    @Override
		    public boolean apply(Integer number) {
		        return (number % 2) != 0;
		    }
		};
		assertTrue(Iterables.all(evenNumbers, Predicates.not(acceptOdd)));
	}
	
	/**
	 * apply a simple function
	 */
	@Test
	public void test07() {
		List<Integer> numbers = Lists.newArrayList(1, 2, 3);
		List<String> asStrings = Lists.transform(numbers, Functions.toStringFunction());
		assertThat(asStrings, contains("1", "2", "3"));
	}
	
	/**
	 * sort collection by first applying an intermediary function
	 */
	@Test
	public void test08() {
		List<Integer> numbers = Arrays.asList(2, 1, 11, 100, 8, 14);
		
		// 排序规则
		Ordering<Object> ordering = Ordering.natural().onResultOf(Functions.toStringFunction());
		
		// Returns a mutable list containing elements sorted by this ordering
		List<Integer> inAlphabeticalOrder = ordering.sortedCopy(numbers);
		
		List<Integer> correctAlphabeticalOrder = Lists.newArrayList(1, 100, 11, 14, 2, 8);
		
		assertThat(correctAlphabeticalOrder, equalTo(inAlphabeticalOrder));
	}
	
	/**
	 * chaining predicates and functions
	 */
	@Test
	public void test09() {
		List<Integer> numbers = Arrays.asList(2, 1, 11, 100, 8, 14);
		Predicate<Integer> acceptEvenNumber = new Predicate<Integer>() {
		    @Override
		    public boolean apply(Integer number) {
		        return (number % 2) == 0;
		    }
		};
		Function<Integer, Integer> powerOfTwo = new Function<Integer, Integer>() {
		    @Override
		    public Integer apply(Integer input) {
		        return (int) Math.pow(input, 2);
		    }
		};
		 
		FluentIterable<Integer> powerOfTwoOnlyForEvenNumbers = FluentIterable.from(numbers)
																			 .filter(acceptEvenNumber)
																			 .transform(Functions.compose(powerOfTwo, powerOfTwo));
		assertThat(powerOfTwoOnlyForEvenNumbers, contains(4*4, 10000*10000, 64*64, 196*196));
	}
	
	/**
	 * create a Map backed by a Set and a Function
	 */
	@Test
	public void test10() {
		Function<Integer, Integer> powerOfTwo = new Function<Integer, Integer>() {
		    @Override
		    public Integer apply(Integer input) {
		        return (int) Math.pow(input, 2);
		    }
		};
		Set<Integer> lowNumbers = Sets.newHashSet(2, 3, 4);
		 
		Map<Integer, Integer> numberToPowerOfTwoMuttable = Maps.asMap(lowNumbers, powerOfTwo);
		Map<Integer, Integer> numberToPowerOfTwoImuttable = Maps.toMap(lowNumbers, powerOfTwo);
		assertThat(numberToPowerOfTwoMuttable.get(2), equalTo(4));
		assertThat(numberToPowerOfTwoImuttable.get(2), equalTo(4));
	}
	
	/**
	 * create a Function from a Predicate
	 */
	@Test
	public void test11() {
		List<Integer> numbers = Lists.newArrayList(1, 2, 3, 6);
		Predicate<Integer> acceptEvenNumber = new Predicate<Integer>() {
		    @Override
		    public boolean apply(Integer number) {
		        return (number % 2) == 0;
		    }
		};
		Function<Integer, Boolean> isEventNumberFunction = Functions.forPredicate(acceptEvenNumber);
		List<Boolean> areNumbersEven = Lists.transform(numbers, isEventNumberFunction);
		 
		assertThat(areNumbersEven, contains(false, true, false, true));
	}
	
	/**
	 * 
	 */
	@Test
	public void test12() {
		
	}
	
	/**
	 * 
	 */
	@Test
	public void test13() {
		
	}
	
	/**
	 * 
	 */
	@Test
	public void test14() {
		
	}
	
	/**
	 * 
	 */
	@Test
	public void test15() {
		
	}
	
	/**
	 * 
	 */
	@Test
	public void test16() {
		
	}
}
