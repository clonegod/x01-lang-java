package test;

import event.MyButtonEvent;
import event.handler.MyEventHandler;
import event.listener.MyButttonEventListenerAdapter;

public class EventTest {
	public static void main(String[] args) {
		
		// 事件源- 内部封装事件相关属性
		final MyButtonEvent me = new MyButtonEvent("evt-01", "按钮1");
		
		// （复杂）事件处理器
		MyEventHandler handler = new MyEventHandler();
		
		// 注册事件监听器到事件源对象上
		me.addEventListener(new MyButttonEventListenerAdapter(handler) {

			@Override
			public void onClick(MyButtonEvent event) {
				System.out.println("click..."+event.toString()); // 简单事件由listener直接处理
			}

			@Override
			public void onDoubleClick(MyButtonEvent event) {
				this.getHandler().process(me); // 复杂事件委托给handler来处理
			}
			
		});
		
		// 简单事件 - 由监听器直接处理
		me.click();
		
		// 复杂事件 - 委托给handler进行处理
		me.dbClick();
		
		// 其它事件 - 未定义事件不进行响应
		me.action();
	}
}
