package demo.guava.basic.object;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ObjectUtilitiesTest {

	public static void main(String[] args) {
		
		List<Book> books = Arrays.asList(
				new Book(new Person("Alice", 20), "java", "Oracle", "11111", 88.11),
				new Book(new Person("Alice", 20), "guava", null, "22222", 88.12),
				new Book(new Person("Bobbb", 20), "nio", "Oracle", "3333", 88.13),
				new Book(new Person("Bobbb", 18), "concurrent", "Oracle", "4444", 88.13)
		);
		Collections.sort(books);
		
		for(Book book : books)  {
			System.out.println(book);
		}
	}
}
