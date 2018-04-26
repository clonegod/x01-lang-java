package builder.instance;

public class TestComplexClassBuilder {
	
	public static void main(String[] args) {
		
		ComplexClass instance = 
				new ComplexClassBuilder().attr1("value1").attr2("value2").build();
		
		System.out.println(instance);
	}
}
