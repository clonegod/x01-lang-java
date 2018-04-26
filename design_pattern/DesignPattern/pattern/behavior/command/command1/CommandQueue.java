package command1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 工作队列：
 *	在某一端添加命令，线程从任务队列中取出1个命令，调用它的execute()，
 *	等这个命令执行完成后，将此命令对象丢弃。再取下一个命令。。。
 */
public class CommandQueue {
	
	// 阻塞队列
	private final BlockingQueue<Command> queue = new ArrayBlockingQueue<>(20);
	
	/**
	 * 向队列添加命令对象
	 */
	public void addCommand(Command c) throws InterruptedException {
		queue.put(c);
	}
	
	/**
	 * 从队列取出1个命令对象
	 * @return
	 * @throws InterruptedException
	 */
	public Command takeCommand() throws InterruptedException {
		// return queue.take(); // 一直阻塞，直到有元素可取
		// return queue.poll(); // 取队首元素，如果队列为空，则返回null
		return queue.poll(3, TimeUnit.SECONDS); // 取队首元素，如果队列为空，则等待若干秒后再取，仍未空则返回null
	}
}
