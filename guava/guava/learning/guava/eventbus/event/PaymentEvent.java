package guava.eventbus.event;

public class PaymentEvent implements Event {

	Object change;
	
	public PaymentEvent(Object change) {
		this.change = change;
	}

	@Override
	public Object getEvent() {
		return this.change;
	}

	@Override
	public String toString() {
		return "PaymentEvent [change=" + change + "]";
	}
	
}
