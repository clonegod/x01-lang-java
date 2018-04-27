package demo.guava.collection.list;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

import demo.guava.basic.object.Person;

public class ListsTest {
	@Test
	public void testNewList() {
		List<Person> personList = Lists.newArrayList();	
		System.out.println(personList);
	}
	
	@Test
	public void testListPartition() {
		List<String> list =
				Lists.newArrayList("A", "B", "C", "D", "E");
		List<List<String>> listlist = Lists.partition(list, 2);
		System.out.println(listlist);
	}
}
