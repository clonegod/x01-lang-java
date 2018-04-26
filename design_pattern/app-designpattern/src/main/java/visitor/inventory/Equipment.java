package visitor.inventory;

import visitor.Visitor;

public abstract class Equipment {
	
	
	public abstract void accept(Visitor vis);
	
	public abstract double price();
	
}
