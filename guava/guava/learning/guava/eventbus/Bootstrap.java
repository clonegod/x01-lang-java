package guava.eventbus;

import com.google.common.eventbus.EventBus;

import guava.event.Event;
import guava.event.LogInfoEvent;
import guava.event.LogWarnEvent;
import guava.event.PaymentEvent;
import guava.eventbus.handler.DeadEventHandler;
import guava.eventbus.handler.LogEventHandler;
import guava.eventbus.handler.PayEventHandler;

/**
 * EventBus: 
 * 	Publish-subscribe-style communication between components 
 * 	without requiring the components to explicitly register with one another.
 *
 */
public class Bootstrap {
	static EventBus eventBus = new EventBus();
	
	public static void main(String[] args) {
		// somewhere during initialization
		// 注册事件处理器
		// 事件处理器可以处理哪些事件，由标注了@Subscribe注解的方法的参数类型决定，可处理的事件范围：属于该类型及其子类型的事件消息。
		// 要让一个事件处理器可接收到更多类型的事件，则参数类型可设置为这些类型的父类型。
		eventBus.register(new LogEventHandler());
		eventBus.register(new PayEventHandler());
		eventBus.register(new DeadEventHandler());
		
		// 业务操作
		customerPay();
		
		// 发送事件
		eventBus.post(new LogInfoEvent("customer pay ..."));
		eventBus.post(new LogWarnEvent("balance not sufficient"));
	}
	
	
	public static void customerPay() {
	  Event event = new PaymentEvent("alice pay $100.00");
	  eventBus.post(event);
	}
}
