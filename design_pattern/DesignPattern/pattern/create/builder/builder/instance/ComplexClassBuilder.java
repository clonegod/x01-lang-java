package builder.instance;

public class ComplexClassBuilder {
	private String attr1;
	private String attr2;
	
	public ComplexClassBuilder attr1(String value) {
		this.attr1 = value;
		return this;
	}
	
	public ComplexClassBuilder attr2(String value) {
		this.attr2 = value;
		return this;
	}
	
	public ComplexClass build() {
		return new ComplexClass(attr1, attr2);
	}
}
