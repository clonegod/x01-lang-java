package prototype3;

import java.io.Serializable;

class Address implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String street;
	public String doorNo;

	public Address(String street, String doorNo) {
		super();
		this.street = street;
		this.doorNo = doorNo;
	}

	@Override
	public String toString() {
		return "Address [street=" + street + ", doorNo=" + doorNo + "]";
	}
}
