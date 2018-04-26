package demo.guava.basic.bloomfilter;

import com.google.common.base.Charsets;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

import demo.guava.basic.object.Book;

public enum BookFunnel implements Funnel<Book> {
	// This is the single enum value
	FUNNEL;
	
	// 将book对象中的部分属性，以二进制写入PrimitiveSink
	// The ISBN property (as a byte array) from Book and Price (double data types)
	// are put into the PrimitiveSink instance and will be used to create the hash
	// code representing the Book instance that is passed in.
	public void funnel(Book from, PrimitiveSink into) {
		into.putBytes(from.getIsbn().getBytes(Charsets.UTF_8))
			.putDouble(from.getPrice());
	}
}