package guava.basic.utilities;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;

/**
 * Object common methods
 *
 */
public class Test03CommonObjectUtilities {
	
	/**
	 * equals - 比较对象是否满足equals的语义
	 */
	@Test(expected=NullPointerException.class)
	public void testEquals1() {
		String str = Math.random() > -1 ? null : "";
		str.equals("");
	}
	
	// JDK 7 provides the equivalent Objects.equals method.
	@Test
	public void testEquals2() {
		Objects.equal("a", "a"); // returns true
		Objects.equal(null, "a"); // returns false
		Objects.equal("a", null); // returns false
		Objects.equal(null, null); // returns true
	}
	
	
	
	
	/**
	 * hashCode -  instead of building the hash by hand.
	 */
	// JDK 7 provides the equivalent Objects.hash(Object...).
	@Test
	public void testHashcode() {
		Stu1 stu1 = new Stu1("s", 1, 1, "1");
		Stu2 stu2 = new Stu2("s", 1, 1, "1");
		System.out.println(stu1.hashCode());
		System.out.println(stu2.hashCode());
	}
	
	static class Stu1 {
		private String name;
		private int age;
		private int grade;
		private String stuNo;
		
		public Stu1(String name, int age, int grade, String stuNo) {
			this.name = name;
			this.age = age;
			this.grade = grade;
			this.stuNo = stuNo;
		}

		@Override
		public int hashCode() {
			System.out.println("hashcode from stu1");
			final int prime = 31;
			int result = 1;
			result = prime * result + age;
			result = prime * result + grade;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((stuNo == null) ? 0 : stuNo.hashCode());
			return result;
		}
	}
	static class Stu2 extends Stu1{
		public Stu2(String name, int age, int grade, String stuNo) {
			super(name, age, grade, stuNo);
		}

		@Override
		public int hashCode() {
			System.out.println("hashcode from stu2");
			return Objects.hashCode(super.age, super.grade, super.name, super.stuNo);
		}
	}
	
	
	
	
	/**
	 * toString
	 */
	@Test
	public void testBeanToString() {
		User bean = new User();
		bean.name = "alcie";
		bean.age = 18;
		System.out.println(bean);
		System.out.println(bean.toString1());
		System.out.println(bean.toString2());
	}
	
	static class User {
		String name;
		int age;
		public String toString1() {
			// Returns "ClassName{x=1}"
		   return MoreObjects.toStringHelper(this)
		       .add("name", name)
		       .add("age", age)
		       .toString();
		}
		public String toString2() {
			// Returns "MyObject{x=1}"
			return MoreObjects.toStringHelper("MyUser-"+name)
					.add("name", name)
					.add("age", age)
					.toString();
		}
	}
	
	/**
	 * compare/compareTo
	 */
	@Test
	public void testCompareTo() {
		Person alice = new Person("alice", "A", 100);
		Person bob = new Person("alice", "A", 90);
		Person cindy = new Person("cindy", "B", 80);
		List<Person> persons = Lists.newArrayList(cindy, alice, bob);
		Collections.sort(persons);
		persons.forEach(System.out::println);
	}
	
	class Person implements Comparable<Person> {
		private String lastName;
		private String firstName;
		private int zipCode;

		public Person(String lastName, String firstName, int zipCode) {
			this.lastName = lastName;
			this.firstName = firstName;
			this.zipCode = zipCode;
		}

		// old way
//		public int compareTo(Person other) {
//			int cmp = lastName.compareTo(other.lastName);
//			if (cmp != 0) {
//				return cmp;
//			}
//			cmp = firstName.compareTo(other.firstName);
//			if (cmp != 0) {
//				return cmp;
//			}
//			return Integer.compare(zipCode, other.zipCode);
//		}
		
		// 链式编程，简洁，并且 可读性强
		public int compareTo(Person that) {
		     return ComparisonChain.start()
		         .compare(this.lastName, that.lastName)
		         .compare(this.firstName, that.firstName)
		         .compare(this.zipCode, that.zipCode)
		         .result();
		   }
		
		@Override
		public String toString() {
			return MoreObjects.toStringHelper(this)
					.add("lastName", lastName)
					.add("firstName", firstName)
					.add("zipCode", zipCode)
					.toString();
		}
	}
	
}
