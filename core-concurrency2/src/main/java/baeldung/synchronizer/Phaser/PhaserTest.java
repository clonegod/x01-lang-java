package baeldung.synchronizer.Phaser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Phaser;
/** 
 * Phaser表示“阶段器”，用来解决控制多个线程分阶段共同完成任务的情景问题。
 * Phaser有phase和party两个重要状态， 
 * 	phase表示阶段，party表示每个阶段的线程个数， 
 * 
 * 场景：
 * 	1个项目分为3个阶段：
 * 		阶段A：需要3个Worker完成任务，全部完成后进入阶段B；
 * 		阶段B：需要2个Worker完成任务，全部完成后进入阶段C；
 * 		阶段C：需要1个Worker完成任务，全部完成后整个项目结束；
 */
public class PhaserTest {
	/**
	 * Phaser中阶段的推进完全由Worker线程来自动控制。当所有注册到Phaser的线程都到达后，自动触发进入到下一个阶段。
	 * 
	 * 如果需要主线程参与到阶段的流程控制中，则需要增加1个party到Phaser中，可通过以下方式实现：
	 * 		Phaser phaser = new CustomPhaser(1);
	 * 		或者构造完Phaser后，向其注册1个新的party来代表主线程的绑定。
	 */
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newCachedThreadPool();
		
		Phaser phaser = new CustomPhaser(); 
		
		// 阶段A
		int workerCountOfPhaseA = 3;
		executePhase(executor, phaser, workerCountOfPhaseA, "PhaseA-Worker-");
		
		// 阶段B
		int workerCountOfPhaseB = 2;
		executePhase(executor, phaser, workerCountOfPhaseB, "PhaseA-Worker-");
		
		// 阶段C
		int workerCountOfPhaseC = 1;
		executePhase(executor, phaser, workerCountOfPhaseC, "PhaseA-Worker-");
		
		// End
		System.out.println("\nCurrent Phase: " + phaser.getPhase());
		
		System.out.println("Phaser is finished: " + phaser.isTerminated());
		
		executor.shutdown();
	}

	private static void executePhase(ExecutorService executor, Phaser phaser, 
			int workerCountOfPhaseA, String workerNamePrefix)
			throws InterruptedException {
		List<Worker> workers = new ArrayList<>();
		for(int i = 0; i < workerCountOfPhaseA; i++) {
			workers.add(new Worker(workerNamePrefix+i, phaser));
			phaser.register(); // 注册1个party到Phaser
		}
		// 开始执行任务
		List<Future<Object>> futureList1 = executor.invokeAll(workers);
		
		futureList1.stream().forEach(x -> {try {
			x.get(); // 
		} catch (Exception e) {
		}});
		System.out.println("==============================\n");
	}

}

class Worker implements Callable<Object> {
	
	private String workerName;
	private Phaser phaser; // 定义任务执行阶段
	
	public Worker(String workerName, Phaser phaser) {
		super();
		this.workerName = workerName;
		this.phaser = phaser;
	}

	@Override
	public Object call() {
        System.out.println("This is phase " + phaser.getPhase() + ", Thread [" + workerName + "] 到达工作场地");
        
        // 到达，并等待其他线程
		phaser.arriveAndAwaitAdvance();
		
		try {
			System.out.println("This is phase " + phaser.getPhase() + ", 所有Worker到达场地，开始干活..." + phaser.getRegisteredParties());
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 到达，并解除与当前Phaser的绑定关系
			System.out.println("Worker" + workerName + "干完活了，解除与Phaser的绑定, phase=" + phaser.getPhase());
			phaser.arriveAndDeregister();
		}
		
		return null;
	}
}
