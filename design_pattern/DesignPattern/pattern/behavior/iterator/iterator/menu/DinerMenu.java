package iterator.menu;

import iterator.iter.DinerMenuIterator;
import iterator.iter.MyIterator;

public class DinerMenu implements Menu {
	static final int MAX_ITEMS = 6;
	int numbersOfItems = 0;
	MenuItem[] menuItems; // 内部数据结构为数组
	
	public DinerMenu() {
		menuItems = new MenuItem[MAX_ITEMS];
		
		addItem("A", "AAAA", true, 2.99);
		addItem("B", "BBBB", true, 2.99);
		addItem("C", "CCCC", true, 2.99);
		addItem("D", "DDDD", true, 2.99);
	}

	private void addItem(String name, String description, boolean vegetarian, double price) {
		MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
		if(numbersOfItems >= MAX_ITEMS) {
			System.err.println("Sorry, menu is full! Can't add item to menu.");
		} else {
			menuItems[numbersOfItems] = menuItem;
			numbersOfItems = numbersOfItems + 1;
		}
	}

	/**
	 * 对外暴露迭代器来访问内部元素
	 */
	@Override
	public MyIterator createIterator() {
		return new DinerMenuIterator(menuItems);
	}
	
}
