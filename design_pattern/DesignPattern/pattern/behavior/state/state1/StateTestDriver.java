package state1;

/**
 * 状态模式与策略模式的异同：
 * 	相同点：
 * 		UML结构图/类图都是一样的。
 * 	不同点：
 *  	状态模式： 主要关注某个对象内部若干不同状态的改变，其行为也随之发生改变。通过该模式，将变化转移到不同的状态类中，避免了大量if条件判断。
 *  			将一组行为封装到状态对象中，当前状态在状态集合中不断发生变化，以反映Context对象内部状态的变化。
 *  			因此，Context的行为也随着当前状态的改变而改变。
 *  			但是，Context的客户端对内部状态是不了解的，对外而言不可见。
 *  
 *  	策略模式： 主要关注于在运行时可灵活切换对象的行为。
 * 				客户端通常需要主动指定Context所要组合的策略对象是哪一个。
 * 				策略模式是除了继承之外的一种弹性替代方案。
 * 				如果继承一个类，该行为将被固定，很难进行修改（可通过复写父类行为来实现，但是这样在某些情况下又没有复用性可言）。
 * 				因此，通过组合不同的对象来改变行为，同时保证了功能的复用性（被复用的行为都封装到被组合对象中了）。
 *
 */
public class StateTestDriver {
	public static void main(String[] args) {
		
		Context context = new Context();
		// 内部状态在不断改变，具体行为也会随之发生改变。但是对客户端而言是不可见的。
		context.handle();
		context.handle();
		context.handle();
		context.handle();
		context.handle();
		context.handle();
		context.handle();
		
	}
}
