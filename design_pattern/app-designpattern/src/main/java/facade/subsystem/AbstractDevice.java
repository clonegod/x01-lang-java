package facade.subsystem;

public abstract class AbstractDevice implements Device {

	public void turnOn() {
		System.out.println(this.getClass().getName()+" trun on");
	}

	public void turnOff() {
		System.out.println(this.getClass().getName()+" trun off");
	}

}
