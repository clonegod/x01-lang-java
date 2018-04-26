package event.listener;

import event.MyButtonEvent;
import event.handler.MyEventHandler;

/**
 * 提供抽象方法的空实现，继承该类的子类可以选择性的覆盖感兴趣的方法。
 *
 */
public class MyButttonEventListenerAdapter implements MyButttonEventListener {
	
	private MyEventHandler handler;
	
	// 对简单事件处理，可直接由listener完成时间响应
	public MyButttonEventListenerAdapter() {
		super();
	}
	
	// 对复杂事件，则委托给handler进行处理
	public MyButttonEventListenerAdapter(MyEventHandler handler) {
		super();
		this.handler = handler;
	}
	

	public MyEventHandler getHandler() {
		return handler;
	}

	public void setHandler(MyEventHandler handler) {
		this.handler = handler;
	}

	public void onClick(MyButtonEvent event) {
	}

	public void onDoubleClick(MyButtonEvent event) {
	}

	public void action01(MyButtonEvent event) {
	}

	public void action02(MyButtonEvent event) {
	}

	public void action03(MyButtonEvent event) {
	}
	
}