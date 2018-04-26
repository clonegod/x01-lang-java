package demo.guava.eventbus.event;

import java.util.Date;

import demo.guava.eventbus.TradeAccount;
import demo.guava.eventbus.TradeType;

public class BuyEvent extends TradeAccountEvent {
	public BuyEvent(TradeAccount tradeAccount, double amount, Date tradExecutionTime) {
		super(tradeAccount, amount, tradExecutionTime, TradeType.BUY);
	}
}