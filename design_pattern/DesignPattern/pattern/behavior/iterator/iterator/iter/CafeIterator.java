package iterator.iter;

import java.util.Collection;
import java.util.Iterator;

import iterator.menu.MenuItem;

public class CafeIterator implements MyIterator {
	Collection<MenuItem> itemColl;
	Iterator<MenuItem> iter;

	public CafeIterator(Collection<MenuItem> items) {
		this.itemColl = items;
		iter = this.itemColl.iterator(); // 初始化迭代器
	}

	@Override
	public boolean hasNext() {
		return iter.hasNext();
	}

	@Override
	public Object next() {
		return iter.next();
	}
	
	
}
