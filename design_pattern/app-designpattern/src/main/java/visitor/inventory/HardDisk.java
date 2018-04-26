package visitor.inventory;

import visitor.Visitor;

public class HardDisk extends Equipment {

	@Override
	public void accept(Visitor vis) {
		System.out.println("HardDisk has been visited");
		vis.visitHardDisk(this);
	}

	@Override
	public double price() {
		return 200.00 ;
	}

}
