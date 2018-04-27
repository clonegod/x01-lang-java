package demo.guava.eventbus.subscriber;

import java.time.LocalDateTime;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import demo.guava.eventbus.event.BuyEvent;

public class TradeBuyAuditor {
	private List<BuyEvent> buyEvents = Lists.newArrayList();
	
	public TradeBuyAuditor(EventBus eventBus) {
		eventBus.register(this);
	}

	@Subscribe
	@AllowConcurrentEvents
	public void auditBuy(BuyEvent buyEvent) throws InterruptedException {
		buyEvents.add(buyEvent);
		Thread.sleep(1000);
		System.out.println("Received TradeBuyEvent " + buyEvent 
				+ " at: " + LocalDateTime.now());
	}

	public List<BuyEvent> getBuyEvents() {
		return buyEvents;
	}
}