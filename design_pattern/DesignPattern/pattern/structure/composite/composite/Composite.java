package composite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Composite extends Component {

	List<Component> components = new ArrayList<>();
	
	public Composite(String name) {
		super(name);
	}
	
	@Override
	public void addComponent(Component component) {
		this.components.add(component);
	}

	@Override
	public void print() {
		System.out.println("\nTREE: " + getName());
		
		Iterator<Component> iter = components.iterator();
		while(iter.hasNext()) {
			iter.next().print();
		}
	}
	
}
