package java7.concurrent;

public class ImmutablByBuilderTest {
	public static void main(String[] args) {
		ImmutablClass.Builder builder = new ImmutablClass.Builder();
		// 使用Builder创建不可变对象
		ImmutablClass instance = builder.field1("f1").field2("f2").build();
		System.out.println(instance.getField1() + "," + instance.getField2());
	}
	
}

// 构建器泛型接口
interface ObjBuilder<T> {
	T build();
}

/**
 * 通过内部静态类（构建器模式）来创建不可变对象。
 * 构建器类拥有与不可变类相同的域字段，但构建器的字段是可以修改的。
 *
 */
class ImmutablClass {
	// 不可变类的字段是final的，只能通过构造函数赋值
	private final String field1;
	private final String field2;
	
	public ImmutablClass(Builder builder) {
		super();
		this.field1 = builder.field1;
		this.field2 = builder.field2;
	}

	public static class Builder implements ObjBuilder<ImmutablClass> {
		// 拥有与外部类相同的字段
		private String field1;
		private String field2;
		
		public Builder field1(String f1) {
			this.field1 = f1;
			return this;
		}
		
		public Builder field2(String f2) {
			this.field2 = f2;
			return this;
		}
		
		@Override
		public ImmutablClass build() {
			return new ImmutablClass(this);
		}
	}

	public String getField1() {
		return field1;
	}

	public String getField2() {
		return field2;
	}
	
}
