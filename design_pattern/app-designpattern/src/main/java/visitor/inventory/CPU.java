package visitor.inventory;

import visitor.Visitor;

public class CPU extends Equipment {

	@Override
	public void accept(Visitor vis) {
		System.out.println("CPU has been visited");
		vis.visitCPU(this);
	}

	@Override
	public double price() {
		return 800.00 ;
	}

}
