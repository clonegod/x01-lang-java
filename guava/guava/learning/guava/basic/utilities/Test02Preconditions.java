package guava.basic.utilities;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkElementIndex;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import org.junit.Test;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * precondition checking
 *	recommend that you split up preconditions into distinct lines, which can help you figure out which precondition failed while debugging
 *	Additionally, you should provide helpful error messages, which is easier when each check is on its own line.
 *
 *Briefly:
 *	After static imports, the Guava methods are clear and unambiguous. checkNotNull makes it clear what is being done, and what exception will be thrown.
 *	checkNotNull returns its argument after validation, allowing simple one-liners in constructors: this.field = checkNotNull(field);.
 *	Simple, varargs "printf-style" exception messages. (This advantage is also why we recommend continuing to use checkNotNull over Objects.requireNonNull)
 */
public class Test02Preconditions {
	
	
	/**
	 * checkArgument(boolean)  - IllegalArgumentException
	 */
	@Test
	public void test1() {
		String arg = null;
		Preconditions.checkArgument(arg != null);
//		Preconditions.checkArgument(arg !=null, "arg must not be null");
//		Preconditions.checkArgument(arg !=null, "arg is null: %s", arg);
	}
	
	/**
	 * checkNotNull(T) - NullPointerException
	 */
	@Test
	public void test2_1() {
		String username = "alice";
		String password = null;
		if(password == null) {
			throw new NullPointerException(String.format("username=%s, password=%s", username, password));
		}
	}
	
	@Test
	public void test2_2() {
		String username = "alice";
		String password = null;
		checkNotNull(password, "username=%s, password=%s", username, password);
	}
	
	@Test
	public void test2_3() {
		String username = "alice";
		String password = null;
		checkArgument(!Strings.isNullOrEmpty(username) && !Strings.isNullOrEmpty(password), 
				"username or password is null or empty! username=%s, password=%s", username, password);
	}
	
	/**
	 * checkState(boolean) - IllegalStateException
	 */
	@Test
	public void test4() {
		String state = "READY";
		
		checkState(state.equals("READY"));
		System.out.println("Starting...");
		
	}
	
	/**
	 * checkElementIndex(int index, int size) - IndexOutOfBoundsException
	 * 
	 * 	ElementIndex  -> from 0 inclusive to size exclusive
	 */
	@SuppressWarnings("unused")
	@Test
	public void test5() {
		String[] userInfo = getUserInfo();
		
		int index = checkElementIndex(0, userInfo.length, "获取mobile数组角标越界");
		String mobile = userInfo[0];
		
		index = checkElementIndex(1, userInfo.length, "获取certNo数组角标越界");
		String certNo = userInfo[index];
		
//		index = checkElementIndex(2, userInfo.length, "获取name数组角标越界");
		index = checkElementIndex(3, userInfo.length, "获取name数组角标越界"); // exception
		String name = userInfo[index];
	}
	
	private String[] getUserInfo() {
		return new String[]{"13622335566","51783320198732211456","阿花"};
	}
	
	
	
	class PreconditionExample {
		private String label;
		private int[] values = new int[5];
		private int currentIndex;

		public PreconditionExample(String label) {
			// returns value of object if not null
			this.label = Preconditions.checkNotNull(label, "Label can''t be null");
		}

		public void updateCurrentIndexValue(int index, int valueToSet) {
			//Check index valid first
			this.currentIndex = Preconditions.checkElementIndex(index, values.length, "Index out of bounds for values");
			//Validate valueToSet
			Preconditions.checkArgument(valueToSet <= 100,"Value can't be more than 100");
			values[this.currentIndex] = valueToSet;
		}

		public void doOperation() {
			Preconditions.checkState(validateObjectState(), "Can't perform operation");
		}

		private boolean validateObjectState() {
			return this.label.equalsIgnoreCase("open") && values[this.currentIndex] == 10;
		}
	}
}
