package thread01.basic;

public class Test05ThreadCommunication {
	
	public static void main(String[] args) {
		
		// 1.创建共享资源
		Res r = new Res();
		
		// 2.创建线程-对共享资源进行赋值
		new Thread(new InputCar(r)).start();
		
		// 3.创建线程-从共享资源读取数据
		new Thread(new OutputCar(r)).start();
	}
	
}

class Res {
	private String name;
	private String sex;
	
	private boolean setted;
	
	// 生产者 - 对共享资源的写操作通过 this 锁进行控制
	public synchronized void set(String name, String sex) {
		if(setted) {
			try { this.wait(); } catch (InterruptedException e) { e.printStackTrace(); }
		}
			
		this.name = name;
		this.sex = sex;
		
		setted = true;
		this.notify(); // 唤醒该锁上等待的某个线程
		// this.notifyAll(); // 唤醒该锁上等待的所有线程
	}
	
	// 消费者 - 对共享资源的读操作通过 this 锁进行控制
	public synchronized void print() {
		if(! setted) {
			try { this.wait(); } catch (InterruptedException e) { e.printStackTrace(); }
		}
		
		System.out.println(this.toString());
		
		setted = false; // 更新标记
		this.notify(); // 唤醒对方线程
	}

	@Override
	public String toString() {
		return "Res [name=" + name + ", sex=" + sex + "]";
	}
	
}

/**
 * 往共享资源Res中设置姓名和性别
 *
 */
class InputCar implements Runnable {
	
	Res r; //共享资源
	
	public InputCar(Res r) {
		this.r = r;
	}

	public void run() {
		int x = 0;
		while(true) {
			if(x == 0) {
				r.set("mike", "man");
			} else {
				r.set("丽丽", "女女女女女女女女");
			}
			x = (x + 1) % 2; // 实现交替赋值逻辑
		}
	}
}

/**
 * 从共享资源Res中读取姓名和性别
 *
 */
class OutputCar implements Runnable {
	
	Res r; //共享资源
	
	public OutputCar(Res r) {
		this.r = r;
	}
	
	public void run() {
		while(true) {
			r.print();
		}
	}
	
}