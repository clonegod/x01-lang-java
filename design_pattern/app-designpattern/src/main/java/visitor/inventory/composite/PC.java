package visitor.inventory.composite;

import visitor.Visitor;
import visitor.inventory.Case;
import visitor.inventory.HardDisk;

/**
 * 整机
 * @author Administrator
 *
 */
public class PC extends Composite {
	
	public PC() {
		super.add(new IntegratedBoard());
		super.add(new HardDisk());
		super.add(new Case());
	}

	@Override
	public void accept(Visitor vis) {
		System.out.println("PC has been visited");
		super.accept(vis);
	}
	
	
}
