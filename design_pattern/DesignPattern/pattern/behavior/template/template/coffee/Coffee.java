package template.coffee;

public class Coffee extends AbstractCaffeinBeverage {

	@Override
	protected void brew() {
		System.out.println("Dripping coffee through filter");
	}

	@Override
	protected void addCondiments() {
		System.out.println("Adding Sugar and Milk");
	}

}
