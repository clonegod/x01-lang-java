package event.handler;

import event.MyButtonEvent;

/**
 * 事件处理器
 *
 */
public interface EventHandler {
	void process(MyButtonEvent event);
}