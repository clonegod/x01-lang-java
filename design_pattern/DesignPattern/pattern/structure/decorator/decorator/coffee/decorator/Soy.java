package decorator.coffee.decorator;

import decorator.coffee.base.Beverage;
import decorator.coffee.base.CondimentDecorator;

public class Soy extends CondimentDecorator {
	
	Beverage beverage;
	
	public Soy(Beverage beverage) {
		this.beverage = beverage;
	}
	
	@Override
	public String getDescription() {
		return beverage.getDescription() + ", Soy";
	}

	@Override
	public double cost() {
		return .30 + beverage.cost();
	}

}
