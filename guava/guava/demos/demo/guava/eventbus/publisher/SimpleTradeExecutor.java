package demo.guava.eventbus.publisher;

import java.util.Date;

import com.google.common.eventbus.EventBus;

import demo.guava.eventbus.TradeAccount;
import demo.guava.eventbus.TradeType;
import demo.guava.eventbus.event.BuyEvent;
import demo.guava.eventbus.event.SellEvent;
import demo.guava.eventbus.event.TradeAccountEvent;

public class SimpleTradeExecutor {
	private EventBus eventBus;

	public SimpleTradeExecutor(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	public void executeTrade(TradeAccount tradeAccount, 
			double amount, TradeType tradeType) {
		TradeAccountEvent tradeAccountEvent = processTrade(tradeAccount, amount, tradeType);
		eventBus.post(tradeAccountEvent);
	}

	private TradeAccountEvent processTrade(TradeAccount tradeAccount, 
			double amount, TradeType tradeType) {
		Date executionTime = new Date();
		
		String message = String.format("\nProcessed trade for %s of amount %s type @ %s at %4$tF %4$tT", 
				tradeAccount, amount, tradeType, executionTime);
		System.out.println(message);
		
		TradeAccountEvent tradeAccountEvent;
		if (tradeType.equals(TradeType.BUY)) {
			tradeAccountEvent = new BuyEvent(tradeAccount, amount, executionTime);
		} else {
			tradeAccountEvent = new SellEvent(tradeAccount, amount, executionTime);
		}
		
		return tradeAccountEvent;
	}
}