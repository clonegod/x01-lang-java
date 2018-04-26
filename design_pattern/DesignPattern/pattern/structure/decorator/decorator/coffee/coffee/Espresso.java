package decorator.coffee.coffee;

import decorator.coffee.base.Beverage;
import decorator.coffee.base.Beverage.SIZE;

/**
 * 浓缩咖啡
 */
public class Espresso extends Beverage {
	
	public Espresso(SIZE size) {
		this.size = size;
		this.description = "Espresso";
	}

	@Override
	public double cost() {
		switch(size) {
			case TALL: return 2.79;
			case GRANDE: return 2.89;
			case VENTI: return 2.99;
		}
		return 0;
	}

}
