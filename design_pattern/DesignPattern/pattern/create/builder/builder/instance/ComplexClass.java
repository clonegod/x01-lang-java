package builder.instance;

public class ComplexClass {
	private String attr1;
	private String attr2;
	// more attributes...
	
	private ComplexClass() {
		super();
	}

	public static final class ComplexClassBuilder {
		private ComplexClass complexClass;
		
		// 创建目标对象实例
		private ComplexClassBuilder() {
			complexClass = new ComplexClass();
		}
		
		// 返回Builder对象
		public static ComplexClassBuilder newBuilder() {
			return new ComplexClassBuilder();
		}
		
		// 设置目标对象的属性1
		public ComplexClassBuilder attr1(String value) {
			complexClass.attr1 = value;
			return this;
		}
		
		// 设置目标对象的属性2
		public ComplexClassBuilder attr2(String value) {
			complexClass.attr2 = value;
			return this;
		}
		
		// 属性设置完成，返回目标对象
		public ComplexClass build() {
			return complexClass;
		}
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
