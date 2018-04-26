package demo.guava.basic.bloomfilter;

import java.io.File;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.io.Files;

import demo.guava.basic.object.Book;

/**
 * BloomFilter是用于指示目标元素是否包含在某个集合中。
 * 	如果返回false，则表示不包含，结果是可靠的；
 * 	如果返回true，则可能包含，也可能不包含，结果是不可靠的。 
 * 
 * 应用场景：
 * 	筛选文件时，防止执行不必要或昂贵操作，如磁盘检索。
 * 
 * @author clonegod@163.com
 *
 */
public class BloomFilterTest {
	public static void main(String[] args) throws Exception {
		File booksPipeDelimited = new File("tmp/books.dat");
		List<Book> books = Files.asCharSource(booksPipeDelimited, Charsets.UTF_8)
								.readLines(new BookLineProcessor());
		
		BloomFilter<Book> bloomFilter = BloomFilter.create(BookFunnel.FUNNEL, 5);
		books.forEach(book -> bloomFilter.put(book));
		
		Book book1 = books.get(0);
		System.out.println("book " + book1.getTitle() 
							+ " contained: " + bloomFilter.mightContain(book1));
		
		Book newBook = new Book.Builder().title("Mountain Climbing").isbn("Unkonwn").price(0.00).build();
		System.out.println("book " + newBook.getTitle() 
							+ " contained: " + bloomFilter.mightContain(newBook));
	}
}
