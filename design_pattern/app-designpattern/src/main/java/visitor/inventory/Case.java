package visitor.inventory;

import visitor.Visitor;

public class Case extends Equipment {

	@Override
	public void accept(Visitor vis) {
		System.out.println("Case has been visited");
		vis.visitCase(this);
	}

	@Override
	public double price() {
		return 30.00 ;
	}

}
