package decorator.coffee.base;

public abstract class Beverage {
	
	public static enum SIZE{
		TALL, // 小杯
		GRANDE, // 中杯
		VENTI; // 大杯
	}
	
	protected SIZE size;
	
	protected String description;
	
	public SIZE getSize() {
		return null;
	}
	
	public String getDescription() {
		return description;
	}
	
	public abstract double cost();
}
