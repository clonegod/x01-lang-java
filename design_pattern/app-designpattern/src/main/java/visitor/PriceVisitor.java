package visitor;

import visitor.inventory.CPU;
import visitor.inventory.Case;
import visitor.inventory.HardDisk;
import visitor.inventory.MainBoard;
import visitor.inventory.composite.IntegratedBoard;
import visitor.inventory.composite.PC;

public class PriceVisitor extends Visitor {
	
	private double total;
	
	public PriceVisitor() {
		total = 0.00;
	}
	
	public double value() {
		return total;
	}

	@Override
	public void visitHardDisk(HardDisk e) {
		total += e.price();
	}

	@Override
	public void visitMainBoard(MainBoard e) {
		total += e.price();
	}

	@Override
	public void visitCPU(CPU e) {
		total += e.price();
	}

	@Override
	public void visitPC(PC e) {
		total += e.price();
	}

	@Override
	public void visitCase(Case e) {
		total += e.price();
	}

	@Override
	public void visitIntegratedBoard(IntegratedBoard e) {
		total += e.price();
	}
	
}
