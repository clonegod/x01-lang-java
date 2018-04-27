package demo.guava.collection.fluentIter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import demo.guava.basic.object.Person;

public class FluentIterableFilterTest {

	ArrayList<Person> personList;
	Person person1;
	Person person2;
	Person person3;
	Person person4;

	@Before
	public void setUp() {
		person1 = new Person("Wilma", "Flintstone", 30, "F");
		person2 = new Person("Fred", "Flintstone", 32, "M");
		person3 = new Person("Betty", "Rubble", 31, "F");
		person4 = new Person("Barney", "Rubble", 33, "M");
		personList = Lists.newArrayList(person1, person2, person3, person4);
	}

	/**
	 * filter method, which may remove any or all of the original elements
	 */
	@Test
	public void testFilter() throws Exception {
		Iterable<Person> personsFilteredByAge = FluentIterable.from(personList).filter(new Predicate<Person>() {
			@Override
			public boolean apply(Person input) {
				return input.getAge() > 31;
			}
		});

		// Iterables is a utility class for working with Iterable instances
		assertThat(Iterables.contains(personsFilteredByAge, person1), is(false));
		assertThat(Iterables.contains(personsFilteredByAge, person2), is(true));
		assertThat(Iterables.contains(personsFilteredByAge, person3), is(false));
		assertThat(Iterables.contains(personsFilteredByAge, person4), is(true));
	}

	/**
	 * mapping operation where Function is applied to each element
	 */
	@Test
	public void testTransform() throws Exception {
		FluentIterable<String>	fluentIter = 
				FluentIterable.from(personList).transform(new Function<Person, String>() {
					@Override
					public String apply(Person input) {
						return Joiner.on('#').join(input.getLastName(), input.getFirstName(), input.getAge());
					}
		});
		
		// toList
		List<String> transformedPersonList = fluentIter.toList(); 
		System.out.println(transformedPersonList);
		assertThat(transformedPersonList.get(1), is("Flintstone#Fred#32"));
		
		// toSortedList
		
		// toSet
		ImmutableSet<String> set = fluentIter.toSet();
		System.out.println(set);
		
		// toSortedSet
		
		// toMap
		// 根据key返回一个对应的value
		Function<String, Person> valueFn = new Function<String, Person>() {
			@Override 
			public Person apply(String input) {
				return person1;
			}
		};
		ImmutableMap<String, Person> persons = fluentIter.toMap(valueFn);
		System.out.println(persons);
	}

}
