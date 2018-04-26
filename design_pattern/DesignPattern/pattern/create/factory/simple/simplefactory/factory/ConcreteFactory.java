package simplefactory.factory;

import simplefactory.animal.Duck;
import simplefactory.animal.IAnimal;
import simplefactory.animal.Tiger;

public class ConcreteFactory extends IAnimalFactory {
	
	/**
	 * 简单工厂方法，通过字符串来表达产品的类型
	 */
	@Override
	public IAnimal GetAnimalType(String type) throws Exception {
		switch (type) {
		case "Duck":
			return new Duck();
		case "Tiger":
			return new Tiger();
		default:
			throw new Exception("Animal type : " + type + " cannot be instantiated");
		}
	}
}