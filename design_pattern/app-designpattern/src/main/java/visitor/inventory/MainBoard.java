package visitor.inventory;

import visitor.Visitor;

public class MainBoard extends Equipment {

	@Override
	public void accept(Visitor vis) {
		System.out.println("MainBoard has been visited");
		vis.visitMainBoard(this);
	}

	@Override
	public double price() {
		return 100.00 ;
	}

}
