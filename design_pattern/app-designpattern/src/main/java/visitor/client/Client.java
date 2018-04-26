package visitor.client;

import visitor.InventoryVisitor;
import visitor.PriceVisitor;
import visitor.inventory.Equipment;
import visitor.inventory.composite.PC;

public class Client {
	private static PriceVisitor pv;
	private static InventoryVisitor iv;
	private static Equipment equip;
	
	public static void main(String[] args) {
		equip = new PC();
		pv = new PriceVisitor();
		equip.accept(pv);
		System.out.println("Price: " + pv.value());
		
		System.out.println("======================================");
		
		iv = new InventoryVisitor();
		equip.accept(iv);
		System.out.println("Number of parts: " + iv.size());
		System.out.println("Price: " + iv.value());
		
	}
}
