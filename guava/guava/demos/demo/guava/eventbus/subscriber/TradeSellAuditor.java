package demo.guava.eventbus.subscriber;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import demo.guava.eventbus.event.SellEvent;

public class TradeSellAuditor {
	private List<SellEvent> sellEvents = Lists.newArrayList();

	public TradeSellAuditor(EventBus eventBus) {
		eventBus.register(this);
	}

	@Subscribe
	public void auditSell(SellEvent sellEvent) {
		sellEvents.add(sellEvent);
		System.out.println("Received SellEvent " + sellEvent);
	}

	public List<SellEvent> getSellEvents() {
		return sellEvents;
	}
	
}