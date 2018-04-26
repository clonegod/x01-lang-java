package composite.menu.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import composite.menu.iter.CompositeIterator;

/**
 * Menu存在嵌套结构
 * 
 * 菜单
 * 	子菜单1
 * 		菜单项1
 * 		菜单项2
 * 	子菜单2
 * 		菜单项
 *
 */
public class Menu extends MenuComponent {
	List<MenuComponent> menuComponents = new ArrayList<>();
	
	String name;
	String description;
	
	public Menu(String name, String description) {
		this.name = name;
		this.description = description;
	}

	@Override
	public void add(MenuComponent menuComponent) {
		this.menuComponents.add(menuComponent);
	}

	@Override
	public MenuComponent getChild(int i) {
		return this.menuComponents.get(i);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void print() {
		// 打印主菜单
		System.out.print("\n" + getName());
		System.out.println(", " + getDescription());
		System.out.println("----------------------------");
		
		// 打印子菜单项
		Iterator<MenuComponent> iter = menuComponents.iterator();
		while(iter.hasNext()) {
			iter.next().print();
		}
	}
	
	public Iterator<MenuComponent> createIterator() {
		return new CompositeIterator(this.menuComponents.iterator());
	}

	@Override
	public String toString() {
		return "Menu [name=" + name + "]";
	}
	
}
