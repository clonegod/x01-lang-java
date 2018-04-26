package visitor.inventory.composite;

import java.util.Vector;

import visitor.Visitor;
import visitor.inventory.Equipment;

/**
 * 复合设备类型
 * @author Administrator
 *
 */
public class Composite extends Equipment {
	
	private Vector<Equipment> parts = new Vector<Equipment>(10);
	
	protected Composite add(Equipment part) {
		parts.add(part);
		return this;
	}

	@Override
	public void accept(Visitor vis) {
		for(Equipment e : parts) {
			e.accept(vis);
		}
	}

	@Override
	public double price() {
		double total = 0;
		for(Equipment e : parts) {
			total += e.price();
		}
		return total;
	}

}
