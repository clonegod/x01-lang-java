package iter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestIterator {
/**
By using Iterator we can retrieve the elements from Collection Object in forward direction only.

Methods in Iterator:
	hasNext()
	next()
	remove()
*/
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>(10);
		list.add(1);
		list.add(2);
		
		Iterator<Integer> iter = list.iterator();
		
		System.out.println("size of list: " + list.size());
		
		while(iter.hasNext()) {
			System.out.println("element="+iter.next());
			iter.remove();
		}
		
		System.out.println("size of list: " + list.size());
	}
}
