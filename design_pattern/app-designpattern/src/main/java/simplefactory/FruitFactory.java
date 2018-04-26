package simplefactory;

import java.text.DateFormat;

public class FruitFactory {

	/**
	 * 工厂根据传入的参数决定创建哪一种产品
	 * 
	 * 这些产品都一定属于某一个抽象产品角色才可以
	 * 
	 * 工厂类可以提供多余1个的工厂方法
	 * 
	 * 缺点：增加新的产品必定导致工厂方法的修改---不符合开闭原则
	 * 
	 * @param which
	 * @return
	 * @throws BadFruitException
	 */
	public static Fruit factory(String which) throws BadFruitException {
		if ("apple".equals(which)) {
			return new Apple();
		} else if ("grap".equals(which)) {
			return new Grap();
		} else if ("strawberry".equals(which)) {
			return new Strawberry();
		} else {
			throw new BadFruitException("Bad Fruit Request");
		}
	}

}
