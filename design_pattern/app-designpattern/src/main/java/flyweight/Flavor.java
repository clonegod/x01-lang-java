package flyweight;

/**
 * Flavor-口味，不随环境而改变，可以作为享元对象处理-----同种口味的只创建1个对象
 * 
 * Flavor就是1个享元对象
 */
public class Flavor implements Order {

	private String flavor; // 内蕴状态，在对象被创建后，不会随环境而变化
	
	// 内蕴状态需在对象创建的时候便确定下来，创建后不再改变
	public Flavor(String flavor) {
		this.flavor = flavor;
	}

	/**
	 * Table是外蕴状态，由客户端传入
	 */
	public void serve(Table table) {
		System.out.println("Serving table " + table.getTableNumber() 
				+ " with flavor: " + this.flavor);
	}

	public String getFlavor() {
		return this.flavor;
	}

}
