package command3.receiver;

/**
 * Receiver
 * 
 */
public class Light {
	
	String name;
	
	public Light(String name) {
		super();
		this.name = name;
	}

	public void on() {
		System.out.println(this.getName() + " light is on");
	}

	public void off() {
		System.out.println(this.getName() + " light is off");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
