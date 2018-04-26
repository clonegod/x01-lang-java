package prototype.deepClone;

import java.io.Serializable;

class Address implements Serializable {
	
	String location;

	@Override
	public String toString() {
		return "Address [location=" + location + "]";
	}

	
}