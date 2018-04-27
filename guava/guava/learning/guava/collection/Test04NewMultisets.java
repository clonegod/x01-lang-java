package guava.collection;

import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.BoundType;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;

/**
 * Multiset
 * 	An extension to Set interface to allow duplicate elements.
 * 
 * There are two main ways of looking at this:
 * 	 like an ArrayList<E> without an ordering constraint: ordering does not matter.
 * 	 like a Map<E, Integer>, with elements and counts.
 * 
 * 可以添加重复元素的Set集合
 * 	支持统计每个元素的出现次数
 * 	可以获取去重后的结果
 * 
 */
public class Test04NewMultisets {
	
	@Test
	public void test() {
		SortedMultiset<Integer> latencies = TreeMultiset.create();
		latencies.addAll(Lists.newArrayList(50, 60, 70, 80, 90, 95, 101, 102, 103, 104));
		
		// how many hits to your site had under 100ms latency
		int count = latencies.subMultiset(0, BoundType.CLOSED, 100, BoundType.OPEN).size();
		
		// calculate the overall proportion.
		System.out.println(new Double(count) / latencies.size());
	}
	

	public static void main(String[] args) {

		// create a multiset collection
		Multiset<String> multiset = HashMultiset.create();
		multiset.add("a");
		multiset.add("b");
		multiset.add("c");
		multiset.add("d");
		multiset.add("a");
		multiset.add("b");
		multiset.add("c");
		multiset.add("b");
		multiset.add("b");
		multiset.add("b");
		// print the occurrence of an element
		System.out.println("Occurrence of 'b' : " + multiset.count("b"));
		// print the total size of the multiset
		System.out.println("Total Size : " + multiset.size());
		// get the distinct elements of the multiset as set
		Set<String> set = multiset.elementSet();
		// display the elements of the set
		System.out.print("Set [");
		for (String s : set) {
			System.out.printf("%s\t", s);
		}
		System.out.println("]");
		System.out.println("=====================================");
		
		// display all the elements of the multiset using iterator
		Iterator<String> iterator = multiset.iterator();
		System.out.print("All elements in MultiSet [");
		while (iterator.hasNext()) {
			System.out.printf("%s\t", iterator.next());
		}
		System.out.println("]");
		System.out.println("=====================================");
		
		// display the distinct elements of the multiset with their occurrence
		// count
		System.out.println("MultiSet [");
		for (Multiset.Entry<String> entry : multiset.entrySet()) {
			System.out.println("Element: " + entry.getElement() + ", Occurrence(s): " + entry.getCount());
		}
		System.out.println("]");
		
		// remove extra occurrences
		multiset.remove("b", 2);
		// print the occurrence of an element
		System.out.println("Occurence of 'b' : " + multiset.count("b"));
	
	}

}
