package visitor;

import java.util.Vector;

import visitor.inventory.CPU;
import visitor.inventory.Case;
import visitor.inventory.Equipment;
import visitor.inventory.HardDisk;
import visitor.inventory.MainBoard;
import visitor.inventory.composite.IntegratedBoard;
import visitor.inventory.composite.PC;

public class InventoryVisitor extends Visitor {
	
	private Vector<Equipment> inv;
	
	private double total;
	
	
	public InventoryVisitor() {
		inv = new Vector<Equipment>(10, 5);
	}

	public int size() {
		return inv.size();
	}
	
	// 由于vector中已保存了所有的设备对象，此类也可以计算总价total
	public double value() {
		for(Equipment e : inv) {
			total += e.price();
		}
		return total;
	}

	@Override
	public void visitHardDisk(HardDisk e) {
		inv.add(e);
	}

	@Override
	public void visitMainBoard(MainBoard e) {
		inv.add(e);
	}

	@Override
	public void visitCPU(CPU e) {
		inv.add(e);
	}

	@Override
	public void visitPC(PC e) {
		inv.add(e);
	}

	@Override
	public void visitCase(Case e) {
		inv.add(e);
	}

	@Override
	public void visitIntegratedBoard(IntegratedBoard e) {
		inv.add(e);
	}

}
