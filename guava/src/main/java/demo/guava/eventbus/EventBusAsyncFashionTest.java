package demo.guava.eventbus;

import java.util.concurrent.Executors;

import com.google.common.eventbus.AsyncEventBus;

import demo.guava.eventbus.event.TradeAccountEvent;
import demo.guava.eventbus.publisher.SimpleTradeExecutor;
import demo.guava.eventbus.subscriber.AllTradesAuditor;
import demo.guava.eventbus.subscriber.TradeBuyAuditor;
import demo.guava.eventbus.subscriber.TradeSellAuditor;

/**
 * The AsyncEventBus class offers the exact same functionality as the EventBus, 
 * but uses a provided java.util.concurrent.
 * Executor instance to execute handler methods asynchronously
 * 
 * @author clonegod@163.com
 *
 */
public class EventBusAsyncFashionTest {
	public static void main(String[] args) throws InterruptedException {
		AsyncEventBus tradeEventBus = 
				new AsyncEventBus(
						TradeAccountEvent.class.getName(), 
						Executors.newFixedThreadPool(10));
		
		// register to EventBus
		new TradeBuyAuditor(tradeEventBus);
		new TradeSellAuditor(tradeEventBus);
		AllTradesAuditor allTradesAuditor = new AllTradesAuditor(tradeEventBus);
		
		// 事件发生后，通过线程池并发通知每个Subscriber
		// 注意：要求每个Subscriber内部对Event处理的handler是线程安全的
		SimpleTradeExecutor tradeExecutor = new SimpleTradeExecutor(tradeEventBus);
		
		tradeExecutor.executeTrade(new TradeAccount("111111"), 100.00d, TradeType.BUY);
		tradeExecutor.executeTrade(new TradeAccount("222222"), 100.00d, TradeType.BUY);
		
		allTradesAuditor.unregister();
		tradeExecutor.executeTrade(new TradeAccount("333333"), 100.00d, TradeType.BUY);
	}
}
