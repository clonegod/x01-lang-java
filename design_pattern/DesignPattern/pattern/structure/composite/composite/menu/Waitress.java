package composite.menu;

import java.util.Iterator;

import composite.menu.component.MenuComponent;
import composite.menu.component.MenuItem;

public class Waitress {
	
	private MenuComponent allMenus;

	public Waitress(MenuComponent allMenus) {
		super();
		this.allMenus = allMenus;
	}
	
	public void printMenu() {
		allMenus.print();
	}
	
	// 打印素食菜单项，需要遍历所有的菜单及其子菜单，需要使用自定义的复杂迭代器来实现（内部使用Stack数据结构来实现）
	public void printVegetarianMenu() {
		Iterator<MenuComponent> iter = allMenus.createIterator();
		while(iter.hasNext()) {
			MenuComponent mc = iter.next();
			// 菜单项才有isVegetarian()，菜单是没有这个行为的。
			if(mc instanceof MenuItem) {
				MenuItem mi = (MenuItem) mc;
				if(mi.isVegetarian()) {
					mi.print();
				}
			}
		}
	}
}
