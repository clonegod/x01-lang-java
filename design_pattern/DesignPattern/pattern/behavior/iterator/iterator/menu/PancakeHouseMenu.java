package iterator.menu;

import java.util.ArrayList;

import iterator.iter.MyIterator;
import iterator.iter.PancakeHouseIterator;

public class PancakeHouseMenu implements Menu {
	private ArrayList<MenuItem> menuItems; // 内部数据结构为列表

	public PancakeHouseMenu() {
		this.menuItems = new ArrayList<>();
		
		addItem("1", "1111", true, 2.99);
		addItem("2", "2222", false, 2.99);
		addItem("3", "3333", true, 3.49);
		addItem("4", "4444", true, 3.99);
	}

	private void addItem(String name, String description, boolean vegetarian, double price) {
		MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
		menuItems.add(menuItem);
	}
	
	/**
	 * 对外暴露迭代器来访问内部元素
	 */
	@Override
	public MyIterator createIterator() {
		return new PancakeHouseIterator(menuItems);
	}
	
}
