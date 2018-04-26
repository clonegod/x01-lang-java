package decorator.coffee.coffee;

import decorator.coffee.base.Beverage;
import decorator.coffee.base.Beverage.SIZE;

public class HouseBlend extends Beverage {

	public HouseBlend(SIZE size) {
		this.size = size;
		this.description = "HouseBlend";
	}

	@Override
	public double cost() {
		switch(size) {
			case TALL: return 3.79;
			case GRANDE: return 3.89;
			case VENTI: return 3.99;
		}
		return 0;
	}

}
