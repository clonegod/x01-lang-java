package builder.instance;

public class ComplexClass {
	private String attr1;
	private String attr2;
	// more attributes...
	
	public ComplexClass() {
		super();
	}

	public ComplexClass(String attr1, String attr2) {
		super();
		this.attr1 = attr1;
		this.attr2 = attr2;
	}

	public String getAttr1() {
		return attr1;
	}

	public String getAttr2() {
		return attr2;
	}

	@Override
	public String toString() {
		return "ComplexClass [attr1=" + attr1 + ", attr2=" + attr2 + "]";
	}
	
}
