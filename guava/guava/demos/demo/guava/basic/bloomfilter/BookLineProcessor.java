package demo.guava.basic.bloomfilter;

import java.io.IOException;
import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.io.LineProcessor;

import demo.guava.basic.object.Book;

public class BookLineProcessor implements LineProcessor<List<Book>> {
	private static final Splitter splitter = Splitter.on("|");
	private List<Book> books = Lists.newArrayList();
	Book.Builder builder = new Book.Builder();

	public boolean processLine(String line) throws IOException {
		List<String> parts = Lists.newArrayList(splitter.split(line));
		builder.author(parts.get(0))
				.title(parts.get(1))
				.publisher(parts.get(2))
				.isbn(parts.get(3))
				.price(Double.parseDouble(parts.get(4)));
		books.add(builder.build());
		return true;	
	}

	@Override
	public List<Book> getResult() {
		return books;
	}
}