package guava.collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * The FluentIterable class, which presents a set of powerful interfaces for
 * working with Iterable instances
 *
 */
public class Test02FluentIterable {
	
	Person person1;
	Person person2;
	Person person3;
	Person person4;

	List<Person> personList;
	
	@Before
	public void setUp() {
		person1 = new Person("Wilma", "Flintstone", 30, "F");
		person2 = new Person("Fred", "Flintstone", 32, "M");
		person3 = new Person("Betty", "Rubble", 31, "F");
		person4 = new Person("Barney", "Rubble", 33, "M");
		personList = Lists.newArrayList(person1, person2, person3, person4);
	}

	/**
	 * The FluentIterable.filter method takes a Predicateas an argument. 
	 * Then every element is examined and retained if the given Predicate holds true for it.
	 */
	@Test
	public void testFilter() throws Exception {
		Iterable<Person> filtered = 
				FluentIterable.from(personList)
							  .filter(new Predicate<Person>() {
									@Override
									public boolean apply(Person input) {
										return input.getAge() > 31;
									}
							  });
		
		assertThat(Iterables.contains(filtered, person2), is(true));
		assertThat(Iterables.contains(filtered, person4), is(true));
		assertThat(Iterables.contains(filtered, person1), is(false));
		assertThat(Iterables.contains(filtered, person3), is(false));
	}
	
	
	/**
	 * FluentIterable.transform method is a mapping operation where Function is applied to each element.
	 */
	@Test
	public void testTransform() throws Exception {
		
		List<String> transformedPersonList = 
				FluentIterable.from(personList)
							.transform(new Function<Person, String>() {
								@Override
								public String apply(Person input) {
									return Joiner.on('#').join(input.getLastName(), input.getFirstName(), input.getAge());
								}
							}).toList();
		
		assertThat(transformedPersonList.get(1), is("Flintstone#Fred#32"));
	}

	/**
	 * Returns an Optional containing the first element in this fluent iterable that satisfies the given predicate, if such an element exists. 
	 */
	@Test
	public void testFirstMatch() {
		Optional<Person> person =
			FluentIterable.from(personList)
							.firstMatch(new Predicate<Person>() {
								@Override
								public boolean apply(Person person) {
									return person.age == 30;
								}
							});
		
		if(person.isPresent()) {
			System.out.println("find person: " + person);
		} else {
			System.out.println("NOT FOUND");
		}
		
	}
	
	private static class Person {
		String firstName, lastName; 
		int age;
		String gender;
		public Person(String firstName, String lastName, int age, String gender) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
			this.age = age;
			this.gender = gender;
		}
		public String getFirstName() {
			return firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public int getAge() {
			return age;
		}
		public String getGender() {
			return gender;
		}
		@Override
		public String toString() {
			return "Person [firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + ", gender=" + gender
					+ "]";
		}
		
	}
}
