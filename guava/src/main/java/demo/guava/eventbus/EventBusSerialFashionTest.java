package demo.guava.eventbus;

import com.google.common.eventbus.EventBus;

import demo.guava.eventbus.event.TradeAccountEvent;
import demo.guava.eventbus.publisher.SimpleTradeExecutor;
import demo.guava.eventbus.subscriber.AllTradesAuditor;
import demo.guava.eventbus.subscriber.TradeBuyAuditor;
import demo.guava.eventbus.subscriber.TradeSellAuditor;

/**
 * We stated earlier the importance of ensuring that our event-handling methods 
 * keep the processing light due to the fact that 
 * the EventBus processes all events in a serial fashion
 * 
 * @author clonegod@163.com
 *
 */
public class EventBusSerialFashionTest {
	public static void main(String[] args) throws InterruptedException {
		EventBus tradeEventBus = new EventBus(TradeAccountEvent.class.getName());
		
		// register to EventBus
		new TradeBuyAuditor(tradeEventBus);
		new TradeSellAuditor(tradeEventBus);
		AllTradesAuditor allTradesAuditor = new AllTradesAuditor(tradeEventBus);
		
		// 事件发生后，将以串行方式通知每个Subscriber
		// 注意：该方式要求每个Subscriber快速处理时间并返回
		SimpleTradeExecutor tradeExecutor = new SimpleTradeExecutor(tradeEventBus);
		
		tradeExecutor.executeTrade(new TradeAccount("111111"), 100.00d, TradeType.BUY);
		
		allTradesAuditor.unregister();
		
		tradeExecutor.executeTrade(new TradeAccount("222222"), 100.00d, TradeType.BUY);
		tradeExecutor.executeTrade(new TradeAccount("333333"), 100.00d, TradeType.BUY);
	}
}
