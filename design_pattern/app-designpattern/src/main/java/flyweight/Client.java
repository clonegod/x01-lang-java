package flyweight;

import java.util.LinkedList;
import java.util.List;

public class Client {
	public static void main(String[] args) {
		FlavorFactory factory = new FlavorFactory();
		
		List<Order> list = new LinkedList<Order>();
		list.add(factory.getOrder("a"));
		list.add(factory.getOrder("b"));
		list.add(factory.getOrder("c"));
		list.add(factory.getOrder("d"));
		list.add(factory.getOrder("a"));
		list.add(factory.getOrder("b"));
		list.add(factory.getOrder("x"));
		list.add(factory.getOrder("y"));
		
		int i = 1;
		for(Order o : list) {
			o.serve(new Table(i++));
		}
		
		
		System.out.println(factory.getTotalFlavors());
	}
	
}
