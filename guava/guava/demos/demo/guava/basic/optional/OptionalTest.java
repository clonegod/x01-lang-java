package demo.guava.basic.optional;

import org.junit.Test;

import com.google.common.base.Optional;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import demo.guava.basic.object.Book;

/**
 * Optional:
 *   The true importance of the Optional class is that 
 *   it signals a value is not guaranteed to be present, 
 *   and it forces us to deal with that fact.
 *   
 * Forcing clients to consider the fact that the returned value may not be present.
 * Useful in making our code more robust by avoiding unexpected null values.
 * 
 * @author clonegod@163.com
 *
 */
public class OptionalTest {
	
	@Test
	public void testCrateOptionAndGetValue() {
		// set value
		Optional<String> opt1 = Optional.absent();
		Optional<String> opt2 = Optional.of("");
		Optional<String> opt3 = Optional.fromNullable(null);
		
		// check and get value
		if(opt1.isPresent()) {
			opt1.get();
		}
		
		String value1 = opt1.orNull();
		
		String value2 = opt1.or("defaultValue");
		
	}
	
	/**
	 *  using the static Optional.of method that returns an Optional instance wrapping the given object. 
	 *  We confrm that our instance is available by asserting that the isPresent method returns true. 
	 */
	@Test
	public void testOptionalOfInstance(){
		Book tradeAccount = new Book.Builder().build();
		Optional<Book> bookOptional = Optional.of(tradeAccount);
		assertThat(bookOptional.isPresent(), is(true));
	}
	
	/**
	 * creating an Optional instance using the fromNullable static method.
	 * 
	 * throws IllegalStateExeption due to the fact that there is no instance present
	 */
	@Test(expected = IllegalStateException.class)
	public void testOptionalNull(){
		Optional<Book> bookOptional = Optional.fromNullable(null);
		assertThat(bookOptional.isPresent(), is(false));
		bookOptional.get();
	}
	
}
