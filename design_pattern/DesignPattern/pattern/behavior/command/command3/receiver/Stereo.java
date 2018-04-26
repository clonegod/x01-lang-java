package command3.receiver;

public class Stereo {
	
	String location;
	
	public Stereo(String location) {
		super();
		this.location = location;
	}

	public void on() {
		System.out.println(this.getLocation() + " stereo is on");
	}

	public void setCD() {
		System.out.println(this.getLocation() + " stereo set cd");
	}

	public void setVolumn(int i) {
		System.out.println(this.getLocation() + " stereo set volumn to " + i);
	}
	
	public void off() {
		System.out.println(this.getLocation() + " setero is off");
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
}
