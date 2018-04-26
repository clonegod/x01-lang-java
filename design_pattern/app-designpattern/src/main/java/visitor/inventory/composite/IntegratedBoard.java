package visitor.inventory.composite;

import visitor.Visitor;
import visitor.inventory.CPU;
import visitor.inventory.MainBoard;

/**
 * 集成主板
 * @author Administrator
 *
 */
public class IntegratedBoard extends Composite {
	
	public IntegratedBoard() {
		super.add(new MainBoard());
		super.add(new CPU());
	}

	@Override
	public void accept(Visitor vis) {
		System.out.println("IntegratedBoard has been visited");
		super.accept(vis);
	}
	
}
