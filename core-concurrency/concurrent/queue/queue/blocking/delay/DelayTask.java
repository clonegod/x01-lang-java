package queue.blocking.delay;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import conc.util.CommonUtil;

public class DelayTask implements Delayed {
	private String taskId;
	
	private String taskContent;
	
	private String startTime; // 任务开始时间
	
	private long delayedTime; // 单位ms
	
	long endTime; // 任务结束时间
	
	public DelayTask(String taskId, String taskContent, long delayedTime) {
		this.taskId = taskId;
		this.taskContent = taskContent;
		this.startTime = CommonUtil.currentTime();
		this.delayedTime = delayedTime;
		this.endTime = System.currentTimeMillis() + delayedTime;
	}

	/**
	 * DelayedQueue 队列内部不同元素排序用---延迟更长的放更靠后的位置
	 */
	@Override
	public int compareTo(Delayed o) {
		return Long.valueOf(this.getDelay(TimeUnit.MILLISECONDS))
					.compareTo(o.getDelay(TimeUnit.MILLISECONDS));
	}

	/**
	 * 判断当前任务是否已到延迟时间，如果延迟超时，则可以返回给消费者线程处理
	 * 
	 * @return 与当前时间相比，还剩余的延迟时间。0或小于0，表示超时时间已到。 
	 * the remaining delay; zero or negative values indicate that the delay has already elapsed
	 */
	@Override
	public long getDelay(TimeUnit unit) {
		// return unit.convert(endTime, TimeUnit.MILLISECONDS) - unit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		return this.endTime - System.currentTimeMillis();
	}

	@Override
	public String toString() {
		return "DelayTask [taskId=" + taskId + ", taskContent=" + taskContent + ", startTime=" + startTime
				+ ", delayedTime=" + delayedTime + "ms, endTime=" + endTime + "]";
	}

}
