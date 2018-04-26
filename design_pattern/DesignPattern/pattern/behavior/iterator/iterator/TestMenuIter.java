package iterator;

import java.util.Arrays;
import java.util.List;

import iterator.menu.CafeMenu;
import iterator.menu.DinerMenu;
import iterator.menu.Menu;
import iterator.menu.PancakeHouseMenu;

public class TestMenuIter {
	public static void main(String[] args) {
		Menu pancakeHouseMenu = new PancakeHouseMenu();
		Menu dinerMenu = new DinerMenu();
		Menu cafeMenu = new CafeMenu();
		
		List<Menu> menus = Arrays.asList(pancakeHouseMenu, dinerMenu, cafeMenu);
		
		Waitress waitress = new Waitress(menus);
		
		/**
		 * N个Menu，每个Menu下有N个Item，需要打印出所有的菜单项。
		 * 麻烦的地方在于：不同的Menu其内部存储的数据结构是不同的，因此遍历方法也是不同的。
		 * 	比如Menu1底层用数组存储，Menu2用List集合存储，Menu3用Map存储。
		 * 解决办法就是，对这些不同的数据结构，提供一个统一的迭代接口---Iterator
		 * 	在这些不同的Menu中，根据各自底层数据结构的特点来实现Iterator的接口方法。
		 */
		waitress.printMenu();
		
	}
}
