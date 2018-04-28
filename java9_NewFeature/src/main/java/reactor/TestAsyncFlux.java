package reactor;

import org.junit.Test;

import clonegod.commons.log.Console;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class TestAsyncFlux {
	
	// 当前主线程执行
	@Test
	public void test1() {
		Flux.range(0, 5)
			.publishOn(Schedulers.immediate())
			.subscribe(Console::log);
	}
	
	// 单线程执行
	@Test
	public void test2() throws InterruptedException {
		Flux.range(0, 5)
			.publishOn(Schedulers.single())
			.subscribe(Console::log);
		
		Thread.currentThread().join(1 * 1000);
	}
	
	// 弹性执行
	@Test
	public void test3() throws InterruptedException {
		Flux.range(0, 5)
		.publishOn(Schedulers.elastic())
		.subscribe(Console::log);
		
		Thread.currentThread().join(1 * 1000);
	}
	
	// 多核并行执行 - 与cpu的核数密切相关
	@Test
	public void test4() throws InterruptedException {
		Flux.range(0, 5)
		.publishOn(Schedulers.parallel())
		.subscribe(Console::log);
		
		Thread.currentThread().join(1 * 1000);
	}
}
