package iterator;

import java.util.Iterator;
import java.util.List;

import iterator.iter.MyIterator;
import iterator.menu.Menu;
import iterator.menu.MenuItem;

public class Waitress {
	List<Menu> menus;
	
	public Waitress(List<Menu> menus) {
		this.menus = menus;
	}
	
	/**
	 * 使用Iterator提供的统一接口，对不同的Menu进行迭代（虽然底层存储MenuItem的数据结构不同）
	 */
	public void printMenu() {
		Iterator<Menu> menuIter = menus.iterator();
		while(menuIter.hasNext()) {
			Menu menu = menuIter.next();
			printMenu(menu.createIterator());
		}
		
	}

	private void printMenu(MyIterator iter) {
		while(iter.hasNext()) {
			MenuItem menuItem = (MenuItem) iter.next();
			System.out.print(menuItem.getName() + ", ");
			System.out.print(menuItem.getPrice() + " -- ");
			System.out.println(menuItem.getDescription());
		}
	}
}
