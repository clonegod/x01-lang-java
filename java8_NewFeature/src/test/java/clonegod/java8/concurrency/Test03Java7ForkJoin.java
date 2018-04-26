package clonegod.java8.concurrency;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.LongAdder;

import com.google.common.collect.Lists;

import clonegod.commons.thread.ThreadUtils;

public class Test03Java7ForkJoin {

	public static void main(String[] args) {
		// 并行： 多核同时参与运算
		
		// 线程池：ForkJoinPool
		System.out.println("ForkJoinPool线程池在当前机器上可达到的并行度: " + ForkJoinPool.commonPool().getParallelism());
		System.out.println("CPU 核心数：" + Runtime.getRuntime().availableProcessors());
		
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		
		List<Integer> numbers = Lists.newArrayList(1,2,3,4,5,6,7,8,9,10);
		
		LongAdder longAdder = new LongAdder();
		
		AddTask forkJoinTask = new AddTask(numbers, longAdder);
		
		forkJoinPool.invoke(forkJoinTask);
		
//		forkJoinPool.invoke(new RecursiveAction() {
//			@Override
//			protected void compute() {
//				System.out.printf("[Thread - %5s] - Hello World\n", 
//						Thread.currentThread().getName());
//			}
//		});
		
		forkJoinPool.shutdown();
		
		System.out.println(longAdder.toString());
	}
	
	// RecursiveAction extends ForkJoinTask<Void>
	private static class AddTask extends RecursiveAction {
		
		private final List<Integer> numbers;
		private final LongAdder longAdder;

		public AddTask(List<Integer> numbers, LongAdder longAdder) {
			this.numbers = numbers;
			this.longAdder = longAdder;
		}

		@Override
		protected void compute() {
			/**
			 * 二分法，进行任务拆分
			 */
			int size = numbers.size();
			
			if(size > 1) {
				
				int middle = size / 2;
				
				List<Integer> leftPart = numbers.subList(0, middle);
				List<Integer> rightPart = numbers.subList(middle, size);
				
				AddTask leftAdd = new AddTask(leftPart, longAdder);
				AddTask rightAdd = new AddTask(rightPart, longAdder);
				
				// 递归
				invokeAll(leftAdd, rightAdd);
				
			} else {
				if (size == 0) { // 保护判断
					return;
				}
				Integer value = numbers.get(0);
				longAdder.add(value.intValue());
				System.out.printf("[thread %s], value=%s, longAdder=%s\n", 
						ThreadUtils.getCurrentThreadName(), value, longAdder);
			}
		}
		
	}
	
}
 