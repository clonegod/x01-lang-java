package composite.menu;

import composite.menu.component.Menu;
import composite.menu.component.MenuComponent;
import composite.menu.component.MenuItem;

public class TestMenuComposite {
	public static void main(String[] args) {
		// 菜单1
		MenuComponent pancakeHouseMenu = new Menu("PANCAKE HOUSE MENU", "Breakfast");
		pancakeHouseMenu.add(new MenuItem("Panckae", "fsaldflas", true, 2.99));
		
		// 菜单2 - 复合菜单
		MenuComponent dinerMenu = new Menu("DINER MENU", "Lunch");
		dinerMenu.add(new MenuItem("Pasta", "alsjflsadjf", true, 3.99));

		// 菜单3
		MenuComponent cafeMenu = new Menu("CAFE MENU", "Dinner");
		cafeMenu.add(new MenuItem("Cafee", "qwenln", false, 2.99));
		
		// 菜单4
		MenuComponent dessertMenu = new Menu("DESSERT MENU", "Dessert of course!");
		dessertMenu.add(new MenuItem("Apple Pie", "kjflsajfnll", true, 1.99));
		
		MenuComponent anotherMenu = new Menu("ANOTHER SUB MENU", "Another sub menu!");
		anotherMenu.add(new MenuItem("Another XXX", "ewrqwaA", true, 0.99));
		
		dinerMenu.add(dessertMenu); // 嵌套子菜单
		dinerMenu.add(anotherMenu); // 嵌套子菜单
		
		Menu allMenus = new Menu("ALL MENUS", "All menus combined");
		//allMenus.add(pancakeHouseMenu);
		allMenus.add(dinerMenu);
		//allMenus.add(cafeMenu);
		
		Waitress waitress = new Waitress(allMenus);
		waitress.printMenu();
		
		System.out.println("======================================");
		waitress.printVegetarianMenu();
	}
}
