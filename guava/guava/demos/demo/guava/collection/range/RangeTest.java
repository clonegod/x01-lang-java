package demo.guava.collection.range;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;

import demo.guava.basic.object.Person;

public class RangeTest {
	/**
	 * create a specifc interval or span of values 
	 * 区间：开闭
	 */
	@Test
	public void testRangeOpenOrClosed() {
		Range<Integer> numberRangeClosed = Range.closed(1,10);
		//both return true meaning inclusive
		assertThat(numberRangeClosed.contains(1), is(true));
		assertThat(numberRangeClosed.contains(10), is(true));
		
		Range<Integer> numberRangeOpen = Range.openClosed(1,10);
		//both return false meaning exclusive
		assertThat(numberRangeOpen.contains(1), is(false));
		assertThat(numberRangeOpen.contains(10), is(true));
		
	}
	
	/**
	 * Range Predicate -> Filter Object In Collection
	 */
	@Test
	public void testRangeToFilterObjects() {
		
		Range<Integer> ageRange = Range.closed(30, 50);
		
		Function<Person, Integer> ageFunction = 
			new Function<Person, Integer>() {
				@Override
				public Integer apply(Person person) {
					return person.getAge();
				}
			};
		
		Predicate<Person> predicate =
				Predicates.compose(ageRange, ageFunction);
	
		List<Person> personAgeInRange = 
				FluentIterable.from(personList).filter(predicate).toList();
		
		System.out.println(personAgeInRange);
	}
	
	ArrayList<Person> personList;
	Person person1;
	Person person2;
	Person person3;
	Person person4;

	@Before
	public void setUp() {
		person1 = new Person("Wilma", "Flintstone", 20, "F");
		person2 = new Person("Fred", "Flintstone", 32, "M");
		person3 = new Person("Betty", "Rubble", 41, "F");
		person4 = new Person("Barney", "Rubble", 53, "M");
		personList = Lists.newArrayList(person1, person2, person3, person4);
	}
}
