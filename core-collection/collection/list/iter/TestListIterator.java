package iter;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class TestListIterator {
/**
where as a ListIterator allows you to traverse in either directions (Both forward and backward). 
So it has two more methods like hasPrevious() and  previous() other than those of Iterator. 
Also, we can get indexes of the next or previous elements (using nextIndex() and previousIndex() respectively )

Methods in ListIterator: 支持更多的方法，允许向前、向后遍历
	hasNext()
	next()
	nextIndex()
	hasPrevious()
	previous()
	previousIndex()
	remove()
	add()
	set()
*/
	
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>(16);
		list.add(1);
		list.add(2);
		
//		IntStream.rangeClosed(1, 10).forEach(list::add);
		
		int fromIndex = 0;
		ListIterator<Integer> listIter = list.listIterator(fromIndex);
		if(listIter.hasPrevious()) {
			System.out.println("index=" + listIter.previousIndex() + ", element=" + listIter.previous());
		}
		
		if(listIter.hasNext()) {
			System.out.println("index=" + listIter.nextIndex() + ", element=" + listIter.next());
		}
		
		listIter.add(3);
		listIter.add(4);
		
		System.out.println("size of list: " + list.size());
		
		while(listIter.hasNext()) {
			System.out.println("index=" + listIter.nextIndex() + ", element=" + listIter.next());
			listIter.remove();
		}
		
		System.out.println("size of list: " + list.size());
		
		System.out.println(list);
	}
}
