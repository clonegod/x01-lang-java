package composite.menu.component;

import java.util.Iterator;

import composite.menu.iter.NullIterator;

public class MenuItem extends MenuComponent {
	String name;
	String description;
	boolean vegetarian; // 素食主义者
	double price;
	
	public MenuItem(String name, String description, boolean vegetarian, double price) {
		super();
		this.name = name;
		this.description = description;
		this.vegetarian = vegetarian;
		this.price = price;
	}
	
	@Override
	public void print() {
		System.out.print("MENUITEM: "+getName());
		if(isVegetarian()) {
			System.out.print("(v)");
		}
		System.out.println(", " + getPrice());
		System.out.println("	-- " + getDescription() + "\n");
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isVegetarian() {
		return vegetarian;
	}
	public void setVegetarian(boolean vegetarian) {
		this.vegetarian = vegetarian;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public Iterator<MenuComponent> createIterator() {
		return new NullIterator();
	}
	
	
}
