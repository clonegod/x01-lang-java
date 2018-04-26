package demo.guava.basic.object;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 *  equals
 *  hashcode
 *  firstNonNull
 *  toStringHelper
 */
public class ObjectsTester {
	public static void main(String[] args) {
		Student s1 = new Student("Mahesh", "Parashar", 1, "VI");
		Student s2 = new Student("Suresh", null, 3, null);
		
		Object notNullStu = MoreObjects.firstNonNull(null, s1);
		System.out.println(s1.equals(notNullStu));
		System.out.println(s1.equals(s2));

		System.out.println(s1.hashCode());
		
		System.out.println(s1.toString());
	}
}

class Student {
	private String firstName;
	private String lastName;
	private int rollNo;
	private String className;

	public Student(String firstName, String lastName, int rollNo, String className) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.rollNo = rollNo;
		this.className = className;
	}

	/**
	 * 重写equals
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Student) || object == null) {
			return false;
		}
		Student student = (Student) object;
		// no need to handle null here
		// Objects.equal("test", "test") == true
		// Objects.equal("test", null) == false
		// Objects.equal(null, "test") == false
		// Objects.equal(null, null) == true
		return Objects.equal(firstName, student.firstName) // first name can be null
				&& Objects.equal(lastName, student.lastName) // last name can be null
				&& Objects.equal(rollNo, student.rollNo) 
				&& Objects.equal(className, student.className);// class name can be null
	}

	/**
	 * 重写hashcode
	 */
	@Override
	public int hashCode() {
		// no need to compute hashCode by self
		return Objects.hashCode(className, rollNo);
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("Name", this.getFirstName() + " " + this.getLastName())
				.add("Class", this.getClassName())
				.add("Roll No", this.getRollNo())
				.toString();
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getRollNo() {
		return rollNo;
	}

	public String getClassName() {
		return className;
	}

}
