package demo.guava.eventbus.event;

import java.util.Date;

import demo.guava.eventbus.TradeAccount;
import demo.guava.eventbus.TradeType;

import static com.google.common.base.Preconditions.*;

public class TradeAccountEvent {
	private double amount;
	private Date tradeExecutionTime;
	private TradeType tradeType;
	private TradeAccount tradeAccount;
	
	public TradeAccountEvent(TradeAccount account, double amount, 
			Date tradeExecutionTime, TradeType tradeType) {
		checkArgument(amount > 0.0, "Trade can't be less than zero");
		this.amount = amount;
		this.tradeExecutionTime = checkNotNull(tradeExecutionTime,"ExecutionTime can't be null");
		this.tradeAccount = checkNotNull(account,"Account can't be null");
		this.tradeType = checkNotNull(tradeType,"TradeType can't be null");
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getTradeExecutionTime() {
		return tradeExecutionTime;
	}
	public void setTradeExecutionTime(Date tradeExecutionTime) {
		this.tradeExecutionTime = tradeExecutionTime;
	}
	public TradeType getTradeType() {
		return tradeType;
	}
	public void setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}
	public TradeAccount getTradeAccount() {
		return tradeAccount;
	}
	public void setTradeAccount(TradeAccount tradeAccount) {
		this.tradeAccount = tradeAccount;
	}
	
}
