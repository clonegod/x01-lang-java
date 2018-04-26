package composite;

public abstract class Component {
	protected String name;
	
	public Component(String name) {
		this.name = name;
	}
	
	public abstract void print();

	public void addComponent(Component component) {
		throw new UnsupportedOperationException();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
