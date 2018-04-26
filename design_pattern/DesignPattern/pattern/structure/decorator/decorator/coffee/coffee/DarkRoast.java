package decorator.coffee.coffee;


import decorator.coffee.base.Beverage;
import decorator.coffee.base.Beverage.SIZE;

public class DarkRoast extends Beverage {

	public DarkRoast(SIZE size) {
		this.size = size;
		this.description = "DarkRoast";
	}

	@Override
	public double cost() {
		switch(size) {
			case TALL: return 0.79;
			case GRANDE: return 0.89;
			case VENTI: return 0.99;
		}
		return 0;
	}

}
