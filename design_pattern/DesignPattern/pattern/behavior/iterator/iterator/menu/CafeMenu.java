package iterator.menu;

import java.util.HashMap;

import iterator.iter.CafeIterator;
import iterator.iter.MyIterator;

public class CafeMenu implements Menu {
	
	HashMap<String, MenuItem> menuItems = new HashMap<>(); // 内部数据结构为哈希表
	
	public CafeMenu() {
		addItem("A1", "a111", true, 3.99);
		addItem("A2", "a222", true, 3.99);
		addItem("A3", "a333", true, 3.99);
	}

	private void addItem(String name, String description, boolean vegetarian, double price) {
		MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
		menuItems.put(menuItem.getName(), menuItem);
	}

	@Override
	public MyIterator createIterator() {
		return new CafeIterator(menuItems.values());
	}
	
}
