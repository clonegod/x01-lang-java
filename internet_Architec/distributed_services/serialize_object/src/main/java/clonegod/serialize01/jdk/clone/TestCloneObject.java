package clonegod.serialize01.jdk.clone;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class TestCloneObject {
	/**
	 * 浅拷贝对象
	 */
	@Test
	public void testShallowCloneObject() throws InterruptedException {
		System.out.println("=============对象浅拷贝=============");
		
		Teacher teacher = new Teacher("mic");
		Student student = new Student(1, new Date(), teacher);
		System.out.println(student);
		
		Student studentClone = (Student) student.clone();
		
		TimeUnit.SECONDS.sleep(2);
		studentClone.setStuNo(3);
		studentClone.setCreateTime(new Date());
		studentClone.getTeacher().setName("james");  // 修改内部引用对象的属性，造成对原对象的修改
		
		System.out.println(student);
		System.out.println("clone-" + studentClone);
		
	}
	
	/**
	 * 深拷贝对象
	 */
	@Test
	public void testDeepCloneObject() throws InterruptedException {
		System.out.println("=============对象深拷贝=============");
		
		Teacher teacher = new Teacher("mic");
		Student student = new Student(2, new Date(), teacher);
		System.out.println(student);
		
		Student studentClone = student.deepClone();
		
		TimeUnit.SECONDS.sleep(2);
		
		studentClone.setStuNo(4);
		studentClone.setCreateTime(new Date());
		studentClone.getTeacher().setName("james"); // 修改深拷贝对象的属性，不会对原对象造成修改
		
		System.out.println(student);
		System.out.println("clone-" + studentClone);
	}
}
