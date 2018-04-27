package guava.eventbus.handler;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

public class DeadEventHandler {

	/**
	 * 没有被任何Handler处理的Event，都会被传入该方法
	 * 
	 * @param deadEvent
	 */
	@Subscribe
	public void unhandledEvent(DeadEvent deadEvent) {
		System.err.println("未被处理的事件: " 
					+ deadEvent.getEvent().toString());
	}
	
	
}
