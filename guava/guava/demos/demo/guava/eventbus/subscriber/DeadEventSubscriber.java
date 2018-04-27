package demo.guava.eventbus.subscriber;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * When EventBus receives a notifcation of an event through the post method,
 * and there are no registered subscribers, the event is wrapped in an instance of a DeadEvent class.
 * 
 * @author clonegod@163.com
 *
 */
public class DeadEventSubscriber {
	private static final Logger logger = Logger.getLogger(DeadEventSubscriber.class.getSimpleName());

	public DeadEventSubscriber(EventBus eventBus) {
		eventBus.register(this);
	}

	/**
	 * inspect the original event that was undelivered
	 * @param deadEvent
	 */
	@Subscribe
	public void handleUnsubscribedEvent(DeadEvent deadEvent) {
		logger.log(Level.WARNING, "No subscribers for " + deadEvent.getEvent());
	}
}