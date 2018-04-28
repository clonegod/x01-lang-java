package reactor;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import clonegod.commons.log.Console;
import reactor.core.publisher.Flux;

public class TestReactorFlux {
	
	public static void main(String[] args) {
		
		Flux<Integer> flux = Flux.just(1,2,3);
		flux.subscribe(Console::log);
		
		
		Flux.just(4,5,6).subscribe(Console::log);
		
	}
	
	@Test
	public void testReactorFluxLambda() {
		// 链式处理
		Flux.just(7,8,9).map(n -> {
			if(ThreadLocalRandom.current().nextInt(7, 10) == n) 
				throw new RuntimeException("抛1个异常");
			return n;
		}).subscribe(
			Console::log, // 处理数据 onNext()
			Console::error, // 处理异常 onError()
			() -> {
				Console.log("Subscription is complete!");
			}
		);
	}
	
	@Test
	public void testReactorFluxApi() {
		Flux.generate(() -> 0, (value, sink) -> {
			if(value == 3) {
				sink.complete();
			} else {
				sink.next("value=" + value);
			}
			return value + 1;
		}).subscribe(
				Console::log,
				Console::error,
				() -> {
					Console.log("Subscription is complete!");
				});
		
	}
}
