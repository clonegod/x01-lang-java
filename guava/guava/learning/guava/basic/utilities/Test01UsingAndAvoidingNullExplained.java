package guava.basic.utilities;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Optional;

/**
 * What's the point?
 * 	Besides the increase in readability that comes from giving null a name, the biggest advantage of Optional is its idiot-proof-ness. 
 *  给null一个名称，提高程序的可读性；
 *  最大的优势在于： "idiot-proof-ness"（谁都会用，即使是idiot也不会用错） 
 *  
 * 	It forces you to actively think about the absent case if you want your program to compile at all, since you have to actively unwrap the Optional and address that case.
 *  强制你主动去思考值为NULL的情况：将Optional解开，并对值否则存在进行相应处理。
 */
public class Test01UsingAndAvoidingNullExplained {
	
	@Test
	public void test1() {
		String prefix = getPrefix();
		String fullStr = prefix.concat(".data");
		System.out.println(fullStr);
	}
	
	@Test
	public void test2() {
		String prefix = getPrefix();
		if(prefix != null) {
			System.out.println(prefix.concat(".data"));
		} else {
			System.out.println("my".concat(".data"));
		}
	}
	
	@Test
	public void test3() {
		Optional<String> prefix = getPrefixOptinal();
		if(prefix.isPresent()) {
			System.out.println(prefix.get().concat(".data"));
		} else {
			System.out.println("my".concat(".data"));
		}
	}
	
	@Test
	public void test4() {
		Optional<String> prefix = getPrefixOptinal();
		System.out.println(prefix.or("my").concat(".data"));
	}
	
	private static String getPrefix() {
		return getValue();
	}
	
	private static Optional<String> getPrefixOptinal() {
		return Optional.fromNullable(getValue());
	}
	
	private static String getValue() {
		return null;
	}

	//```````````````````````````````````````````````````````````````````````//


	/**
	 * Optional.of(T) - 值不能为null，否则抛异常
	 * 	Make an Optional containing the given non-null value, or fail fast on null.
	 */
	@Test(expected=NullPointerException.class)
	public void makingAnOptional1() {
		String valueMustNotNull = null;
		Optional<String> opt = Optional.of(valueMustNotNull);
		opt.isPresent();
		opt.get();
	}
	
	@Test
	public void makingAnOptional2() {
		String valueMustNotNull = "";
		Optional<String> opt = Optional.of(valueMustNotNull);
		if(opt.isPresent()) {
			System.out.println(opt.get());
		}
	}
	
	
	/**
	 * Optional.absent() - 创建一个不存在值的Optional
	 * 	Return an absent Optional of some type.
	 */
	@Test
	public void makingAnOptional3() {
		Optional<String> opt = Optional.absent();
		Assert.assertFalse(opt.isPresent());
	}
	
	
	/**
	 * Optional.fromNullable(T) - 创建一个可以兼容Null值的Optional
	 * 	Turn the given possibly-null reference into an Optional, treating non-null as present and null as absent.
	 */
	@Test
	public void makingAnOptional4() {
		String allowNullValue = null;
		Optional<String> opt = Optional.fromNullable(allowNullValue);
		if(opt.isPresent()) {
			System.out.println("value="+opt.get());
		} else {
			System.out.println("value not exist");
		}
	}
	
	
	
	/**
	 * Query methods - isPresent() + get()
	 */
	@Test
	public void queryOptional1() {
		String nullableReference = null;
		Optional<String> opt = Optional.fromNullable(nullableReference);
		
		if(opt.isPresent()) {
			System.out.println("value exist:" + opt.get());
		} else {
			System.out.println("value not exist");
		}
		
	}
	
	
	/**
	 * Query methods - or(T)
	 */
	@Test
	public void queryOptional3() {
		Optional<String> opt = Optional.fromNullable("");
		String value = opt.or("default value");
		Assert.assertEquals(value, "");
	}
	
	@Test
	public void queryOptional2() {
		Optional<String> opt = Optional.fromNullable(null);
		String value = opt.or("default value");
		Assert.assertEquals(value, "default value");
	}
	
	
	/**
	 * Query methods - orNull()
	 */
	@Test
	public void queryOptional4() {
		Optional<String> opt = Optional.fromNullable(null);
		String value = opt.orNull();
		Assert.assertNull(value);
	}
	
	/**
	 * Query methods - asSet()
	 */
	@Test
	public void queryOptional5() {
		Optional<String> opt = Optional.fromNullable(null);
		Set<String> value = opt.asSet();
		Assert.assertTrue(value.isEmpty());
	}
	
	
	// more。。。
	/*	MoreObjects.firstNonNull(T, T)
	 * 	Strings.emptyToNull(String)
	 * 	Strings.isNullOrEmpty(String)
	 * 	Strings.nullToEmpty(String)
	 */
}
