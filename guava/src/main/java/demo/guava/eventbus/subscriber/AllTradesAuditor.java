package demo.guava.eventbus.subscriber;

import java.time.LocalDateTime;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import demo.guava.eventbus.event.BuyEvent;
import demo.guava.eventbus.event.SellEvent;

public class AllTradesAuditor {
	private List<BuyEvent> buyEvents = Lists.newArrayList();
	private List<SellEvent> sellEvents = Lists.newArrayList();

	private EventBus eventBus;
	
	public AllTradesAuditor(EventBus eventBus) {
		this.eventBus = eventBus;
		this.eventBus.register(this);
	}

	@Subscribe
	public void auditSell(SellEvent sellEvent) {
		sellEvents.add(sellEvent);
				
	}

	@Subscribe
	@AllowConcurrentEvents
	public void auditBuy(BuyEvent buyEvent) throws InterruptedException {
		buyEvents.add(buyEvent);
		Thread.sleep(1000);
		System.out.println("Received TradeBuyEvent " + buyEvent
				+ " at: " + LocalDateTime.now());
	}
	
	public void unregister(){
		this.eventBus.unregister(this);
	}
}