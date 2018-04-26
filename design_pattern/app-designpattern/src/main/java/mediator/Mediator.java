package mediator;

/**
 * 模式比较
 *  -观察者模式
 *  	观察者将其自身注册到主题，当主题发生变化时，通知所有已注册的观察者
 *  
 *  	由观察者的某个状态发生变化而触发事件方法的执行
 *  
 *  	观察者的出发点：
 *  		当某个外部对象的状态发生变化时，其他对象需要同步更新自身的状态
 *  		如，Weather Report
 *  
 *  -调停者模式
 *  	同事对象将其自身注册给调停者，当某个同事对象发生变化时，由调停者通知其他的同事对象
 *  	
 *  	由某个同事对象的状态改变而触发事件方法的执行
 *  
 *  	调停者模式的出发点：
 *  		某个同事对象的状态发生变化后，其它的同事对象也要对此变化做出反应，更新内部状态
 *  		将同事对象间的网状作用关系转为为星状关系，调停者对象在中间进行协调，解耦同事对象间的依赖
 *
 */
public abstract class Mediator {
	
	/**
	 * 同事对象将自己作为参量传递给调停者
	 * @param c
	 */
	public abstract void addConcretColleague(Colleague c);

	/**
	 * 事件方法
	 * @param c
	 */
	public abstract void colleagueChanged(Colleague c);

}
