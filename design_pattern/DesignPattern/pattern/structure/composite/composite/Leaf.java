package composite;

public class Leaf extends Component {
	
	public Leaf(String name) {
		super(name);
	}

	public void print() {
		System.out.println("LEAF: " + this.name);
	}
}
