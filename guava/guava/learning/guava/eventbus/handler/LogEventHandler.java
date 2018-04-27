package guava.eventbus.handler;

import com.google.common.eventbus.Subscribe;

import guava.eventbus.event.LogWarnEvent;

// Class is typically registered by the container.
public class LogEventHandler {
	
//	@Subscribe
//	public void onEvent(LogInfoEvent e) {
//		log(e.getEvent());
//	}
	
	@Subscribe
	public void onEvent(LogWarnEvent e) {
		log(e.getEvent());
	}

	private void log(Object data) {
		System.out.println(this + "---------->>> write to log: " + data);
	}
}