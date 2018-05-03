package java7.forkjoin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 子任务将合并为一个结果
 *
 */
public class CustomRecursiveTask extends RecursiveTask<Integer> {
    private int[] arr;
 
    private static final int THRESHOLD = 20;
 
    public CustomRecursiveTask(int[] arr) {
        this.arr = arr;
    }
 
    @Override
    protected Integer compute() {
        if (arr.length > THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubtasks())
              .stream()
              .mapToInt(ForkJoinTask::join)
              .sum();
        } else {
            return processing(arr);
        }
    }
 
    private Collection<CustomRecursiveTask> createSubtasks() {
        List<CustomRecursiveTask> dividedTasks = new ArrayList<>();
        
        dividedTasks.add(new CustomRecursiveTask(
          Arrays.copyOfRange(arr, 0, arr.length / 2)));
        
        dividedTasks.add(new CustomRecursiveTask(
          Arrays.copyOfRange(arr, arr.length / 2, arr.length)));
        
        return dividedTasks;
    }
 
    // task - filter: 10 < num < 27 , summary
    private Integer processing(int[] arr) {
        return Arrays.stream(arr)
          .filter(a -> a > 10 && a < 27)
          .map(a -> a * 10)
          .sum();
    }
    
    
    public static void main(String[] args) {
    	ForkJoinPool pool = ForkJoinPool.commonPool();
    	
    	int[] array = new int[]{10, 12, 13, 20, 28};
    	
    	Integer result = pool.invoke(new CustomRecursiveTask(array));
    	
    	System.out.println(result);
	}
    
    
}