package template.coffee;

public abstract class AbstractCaffeinBeverage {
	
	/**
	 * 模板方法-专注与算法本身，由子类提供某些步骤的完整实现。
	 * 	Comparable接口，Compartor实现自定义对象的排序，比如Arrays.sort(),Collections.sort()，内部都是用模板方法进行封装的。
	 * 	
	 * 模板方法模式：在一个方法中定义一个算法的骨架，而将一些步骤延迟到子类中。
	 * 			模板方法使得子类可以在不改变算法结构的前提下，重新定义算法中的某些步骤。
	 * 
	 * final 限定： 保护算法不被子类所修改
	 */
	protected final void prepareRecipe() {
		boilWater();
		brew();
		pourInCup();
		// hook
		if(customerWantsCondiments()) {
			addCondiments();
		}
	}
	
	/**
	 * 对于可选的算法，可以通过钩子函数来进行控制
	 */
	protected boolean customerWantsCondiments() {
		return true;
	}

	protected abstract void brew(); // 准备冲泡原料：茶或咖啡
	
	protected abstract void addCondiments(); // 添加辅料

	private void boilWater() {
		System.out.println("Boiling water");
	}

	private void pourInCup() {
		System.out.println("Pouring into cup");
	}

	
}
