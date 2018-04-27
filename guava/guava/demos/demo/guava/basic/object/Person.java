package demo.guava.basic.object;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ComparisonChain;

public class Person implements Comparable<Person> {
	private String firstName;
	private String lastName;
	private int age;
	private String gender;
		
	public Person(String firstName, int age) {
		super();
		this.firstName = firstName;
		this.age = age;
	}

	public Person(String firstName, String lastName, int age, String gender) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
	}

	@Override
	public int compareTo(Person o) {
		return ComparisonChain.start()
				.compare(this.firstName, o.firstName)
				.compare(this.lastName, o.lastName)
				.compare(this.age, o.age)
				.compare(this.gender, o.gender)
				.result();
	}
	
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("firstName", firstName)
				.add("lastName", lastName)
				.add("age", age)
				.add("gender", gender)
				.toString();
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
	
}