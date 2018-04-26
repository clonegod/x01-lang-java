package composite.menu.component;

import java.util.Iterator;

/**
 * 基类提供所有行为的默认实现
 * 	由于MenuItem与SubMenu具有不同的行为，子类按自己的情况来复写需要的方法
 *
 */
public abstract class MenuComponent {
	
	public void add(MenuComponent menuComponent) {
		throw new UnsupportedOperationException();
	}
	
	public void remove(MenuComponent menuComponent) {
		throw new UnsupportedOperationException();
	}
	
	public MenuComponent getChild(int i) {
		throw new UnsupportedOperationException();
	}
	
	public String getName() {
		throw new UnsupportedOperationException();
	}
	
	public double getPrice(MenuComponent menuComponent) {
		throw new UnsupportedOperationException();
	}
	
	public String getDescription() {
		throw new UnsupportedOperationException();
	}
	
	public boolean isVegetarian() {
		throw new UnsupportedOperationException();
	}
	
	public void print() {
		throw new UnsupportedOperationException();
	}
	
	public abstract Iterator<MenuComponent> createIterator();
}
