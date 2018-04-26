package visitor;

import visitor.inventory.CPU;
import visitor.inventory.Case;
import visitor.inventory.HardDisk;
import visitor.inventory.MainBoard;
import visitor.inventory.composite.IntegratedBoard;
import visitor.inventory.composite.PC;

public abstract class Visitor {
	
	public abstract void visitHardDisk(HardDisk e);

	public abstract void visitMainBoard(MainBoard e);

	public abstract void visitCPU(CPU e);

	public abstract void visitPC(PC e);

	public abstract void visitCase(Case e);

	public abstract void visitIntegratedBoard(IntegratedBoard e);
}
