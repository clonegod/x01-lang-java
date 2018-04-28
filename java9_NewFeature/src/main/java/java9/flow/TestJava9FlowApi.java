package java9.flow;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.Flow.Subscription;

import clonegod.commons.log.Console;

public class TestJava9FlowApi {
	
	public static void main(String[] args) throws InterruptedException {
		// 发布者
		try (SubmissionPublisher<String> publisher = new SubmissionPublisher<>();){
			
			// 订阅
			publisher.subscribe(new StringSubscriber("A"));
			publisher.subscribe(new StringSubscriber("B"));
			publisher.subscribe(new StringSubscriber("C"));
			
			
			// 发布
			publisher.submit("Hello,World");
		}
		
		// 等待主线程执行结束 --- 为了强制主线程下的子线程运行
		Thread.currentThread().join(1000);
	}
	
	// 订阅者
	private static class StringSubscriber implements Flow.Subscriber<String> {
		
		String name;
		
		Subscription subscription;
		
		public StringSubscriber(String name) {
			super();
			this.name = name;
		}

		@Override
		public void onSubscribe(Subscription subscription) {
			Console.log("订阅者["+name+"]开始订阅");
			this.subscription = subscription;
			// 向服务器反向发送请求
			subscription.request(1); // back push
		}

		@Override
		public void onNext(String item) {
			Console.log("订阅者["+name+"]接收数据： " + item);
			if(ThreadLocalRandom.current().nextBoolean()) {
				subscription.cancel(); // 取消订阅，后面的订阅都不会再被执行到
			} else {
				throw new RuntimeException("模拟异常情况"); // 异常，导致后面的订阅都不会被执行到
			}
		}

		@Override
		public void onError(Throwable t) {
			Console.error("订阅者["+name+"]订阅异常", t);
		}

		@Override
		public void onComplete() {
			Console.log("订阅者["+name+"]订阅结束");
		}
		
	}
}


