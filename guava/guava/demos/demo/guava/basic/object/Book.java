package demo.guava.basic.object;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

public class Book implements Comparable<Book> {
		private Person author;
		private String title;
		private String publisher;
		private String isbn;
		private double price;
		
		public Book(Person author, String title, String publisher, String isbn, double price) {
			this.author = author;
			this.title = title;
			this.publisher = MoreObjects.firstNonNull(publisher, "工业出版社");;
			this.isbn = isbn;
			this.price = price;
		}

		public int hashCode() {
			return Objects.hashCode(title, author, publisher, isbn);
		}

		public String toString() {
			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("title", title)
					.add("author", author)
					.add("publisher", publisher)
					.add("price", price)
					.add("isbn", isbn)
					.toString();
		}

		@Override
		public int compareTo(Book o) {
			return ComparisonChain.start()
					.compare(this.title, o.getTitle())
					.compare(this.author, o.getAuthor())
					.compare(this.publisher, o.getPublisher())
					.compare(this.isbn, o.getIsbn())
					.compare(this.price, o.getPrice())
					.result();
		}
		
		public static class Builder {
			private String author;
			private String title;
			private String publisher;
			private String isbn;
			private double price;
			public Builder author(String author) {
				this.author = author;
				return this;
			}
			public Builder title(String title) {
				this.title = title;
				return this;
			}
			public Builder publisher(String publisher) {
				this.publisher = publisher;
				return this;
			}
			public Builder isbn(String isbn) {
				this.isbn = isbn;
				return this;
			}
			public Builder price(double price) {
				this.price = price;
				return this;
			}
			public Book build() {
				return new Book(new Person(author, 20), title, publisher, isbn, price);
			}
		}

		public Person getAuthor() {
			return author;
		}

		public String getTitle() {
			return title;
		}

		public String getPublisher() {
			return publisher;
		}

		public String getIsbn() {
			return isbn;
		}

		public double getPrice() {
			return price;
		}
	}