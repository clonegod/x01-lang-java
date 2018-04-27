package demo.guava.collection.ordering;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import demo.guava.basic.object.Person;
public class OrderingTest {
	
	ArrayList<Person> personList;
	Person person1;
	Person person2;
	Person person3;
	Person person4;
	Person person5;

	@Before
	public void setUp() {
		person1 = new Person("Wilma", "Flintstone", 20, "F");
		person2 = new Person("Wilma", "Flintstone", 12, "M");
		person3 = new Person("Betty", "Rubble", 51, "F");
		person4 = new Person("Barney", "Rubble", 51, "M");
		person5 = new Person("Barney", "Rubble", 53, "M");
		personList = Lists.newArrayList(person1, person2, person3, person4, person5);
	}
	
	Comparator<Person> byFirstName = 
			(x,y) -> { return x.getFirstName().compareTo(y.getFirstName()); };
			
	Comparator<Person> byAge = 
			(x,y) -> { return x.getAge() - y.getAge(); };
	
	@Test
	public void testCreateOrderingFromComparator() {
		Ordering<String> order = 
			Ordering.from(new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					return o1.compareTo(o2);
				}
			});
		List<String> list = Lists.newArrayList("B", "C", "A");
		Collections.sort(list, order);
		assertThat(list.get(0), is("A"));
	}
	
	/**
	 * 反转排序规则
	 */
	@Test
	public void testReverseSort() {
		Ordering<Person> secondaryOrdering = 
				Ordering.from(byAge).reverse();
		
		Collections.sort(personList, secondaryOrdering);
		
		assertThat(personList.get(0), is(person5));
	}
	
	/**
	 * Null 前置、Null 后置
	 */
	@Test
	public void testSortNullFirst() {
		List<String> list = Lists.newArrayList("B", "C", null, "A");
		Comparator<String>  c = (x,y) -> {return x.compareTo(y);};
		
		Collections.sort(list, Ordering.from(c).nullsLast());
		System.out.println(list);
		
		Collections.sort(list, Ordering.from(c).nullsFirst());
		System.out.println(list);
	}
	
	/**
	 * 多排序规则
	 */
	@Test
	public void testSecondarySorting() {
		Ordering<Person> secondaryOrdering = 
				Ordering.from(byAge).compound(byFirstName);
		
		Collections.sort(personList, secondaryOrdering);
		
		assertThat(personList.get(0), is(person2));
	}
	
	/**
	 * 取排序后的前N个，后N个
	 */
	@Test
	public void testMinOrMaxInCollection() {
		Person minPerson = Ordering.from(byAge).min(personList);
		System.out.println(minPerson);
		
		List<Person> top2Person = Ordering.from(byAge).greatestOf(personList, 2);
		System.out.println(top2Person);
		
		List<Person> least2Person = Ordering.from(byAge).leastOf(personList, 2);
		System.out.println(least2Person);
		
	}
	
}
