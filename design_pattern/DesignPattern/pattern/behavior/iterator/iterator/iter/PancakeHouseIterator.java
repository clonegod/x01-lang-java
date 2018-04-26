package iterator.iter;

import java.util.ArrayList;

import iterator.menu.MenuItem;

public class PancakeHouseIterator implements MyIterator {
	
	ArrayList<MenuItem> items;
	int position = 0;
	
	public PancakeHouseIterator(ArrayList<MenuItem> items) {
		this.items = items;
	}

	@Override
	public boolean hasNext() {
		return position < items.size();
	}

	@Override
	public Object next() {
		MenuItem menuItem = items.get(position);
		position = position + 1;
		return menuItem;
	}

}
