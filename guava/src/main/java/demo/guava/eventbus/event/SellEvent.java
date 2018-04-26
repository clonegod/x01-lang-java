package demo.guava.eventbus.event;

import java.util.Date;

import demo.guava.eventbus.TradeAccount;
import demo.guava.eventbus.TradeType;

public class SellEvent extends TradeAccountEvent {
	public SellEvent(TradeAccount tradeAccount, double amount, Date tradExecutionTime) {
		super(tradeAccount, amount, tradExecutionTime, TradeType.SELL);
	}
}