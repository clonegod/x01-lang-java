package event.listener;

import event.MyButtonEvent;

/**
 * 具有多种可被监听的事件行为
 *
 */
public interface MyButttonEventListener extends MyEventListener {
	
	void onClick(MyButtonEvent event);
	
	void onDoubleClick(MyButtonEvent event);
	
	void action01(MyButtonEvent event);
	void action02(MyButtonEvent event);
	void action03(MyButtonEvent event);
	
}