package composite;

public class TestTreeComposite {
	public static void main(String[] args) {
		
		Component tree = new Composite("tree");
		
		Component leaf = new Leaf("leaf");
		
		Component subtree01 = new Composite("subtree01");
		subtree01.addComponent(new Leaf("subtree01-leaf01"));
		subtree01.addComponent(new Leaf("subtree01-leaf02"));
		
		Component subtree02 = new Composite("subtree02");
		subtree02.addComponent(new Leaf("subtree02-leaf01"));
		subtree02.addComponent(new Leaf("subtree02-leaf02"));
		
		tree.addComponent(leaf);
		tree.addComponent(subtree01);
		tree.addComponent(subtree02);
		
		tree.print();
		
	}
}
