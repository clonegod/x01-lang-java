package decorator.coffee.coffee;

import decorator.coffee.base.Beverage;
import decorator.coffee.base.Beverage.SIZE;

public class Decaf extends Beverage {

	public Decaf(SIZE size) {
		this.size = size;
		this.description = "Decaf";
	}

	@Override
	public double cost() {
		switch(size) {
			case TALL: return 1.79;
			case GRANDE: return 1.89;
			case VENTI: return 1.99;
		}
		return 0;
	}

}
