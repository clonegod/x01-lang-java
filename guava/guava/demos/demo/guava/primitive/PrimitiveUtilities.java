package demo.guava.primitive;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;

public class PrimitiveUtilities {

	@Test
	public void testBytes() {
		new BytesTester().testBytes();
	}
	
	
	@Test
	public void testInts() {
		new IntsTester().testInts();
	}

	public class BytesTester {
		/**
		 * byte[] 与 List<Byte> 的转化
		 */
		private void testBytes() {
			byte[] byteArray = { 1, 2, 3, 4, 5, 5, 7, 9, 9 };
			// convert array of primitives to array of objects--------------------byte[] -> List<Byte>
			List<Byte> objectArray = Bytes.asList(byteArray);
			System.out.println(objectArray.toString());

			// convert array of objects to array of primitives
			byteArray = Bytes.toArray(objectArray);//-------------------- -> List<Byte> -> byte[]
			System.out.print("[ ");
			for (int i = 0; i < byteArray.length; i++) {
				System.out.print(byteArray[i] + " ");
			}
			System.out.println("]");

			byte data = 5;
			// check if element is present in the list of primitives or not
			System.out.println("5 is in list? " + Bytes.contains(byteArray, data));
			// Returns the index
			System.out.println("Index of 5: " + Bytes.indexOf(byteArray, data));
			// Returns the last index maximum
			System.out.println("Last index of 5: " + Bytes.lastIndexOf(byteArray, data));
		}
	}

	public class IntsTester {
		private void testInts() {
			int[] intArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
			// convert array of primitives to array of objects--------------------int[] -> List<Integer>
			List<Integer> objectArray = Ints.asList(intArray);
			System.out.println(objectArray.toString());
			
			intArray = Ints.toArray(objectArray); //-------------------- -> List<Integer> -> int[]
			System.out.print("[ ");
			for (int i = 0; i < intArray.length; i++) {
				System.out.print(intArray[i] + " ");
			}
			System.out.println("]");
			
			// check if element is present in the list of primitives or not
			System.out.println("5 is in list? " + Ints.contains(intArray, 5));
			
			// Returns the minimum
			System.out.println("Min: " + Ints.min(intArray));
			// Returns the maximum
			System.out.println("Max: " + Ints.max(intArray));
			
			// get the byte array from an integer -------------------- int -> byte[] of hex element
			byte[] byteArray = Ints.toByteArray(20000);
			for (int i = 0; i < byteArray.length; i++) {
				System.out.print("0x" + Integer.toHexString(byteArray[i]) + " ");
			}
			Assert.assertTrue(20000 == Ints.fromBytes((byte)0x0, (byte)0x0, (byte)0x4e, (byte)0x20 ));
			
			List<Integer> ilist = Ints.asList(1, 2, 3, 4, 5);
			System.out.println("\n" + ilist);
		}
	}

}
