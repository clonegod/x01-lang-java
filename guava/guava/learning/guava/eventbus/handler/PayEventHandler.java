package guava.eventbus.handler;

import com.google.common.eventbus.Subscribe;

import guava.eventbus.event.PaymentEvent;

// Class is typically registered by the container.
public class PayEventHandler {
	
	@Subscribe
	public void onEvent(PaymentEvent e) {
		pay(e.getEvent());
	}

	private void pay(Object payment) {
		System.out.println(this + "---------->>> pay: " + payment);
	}
}