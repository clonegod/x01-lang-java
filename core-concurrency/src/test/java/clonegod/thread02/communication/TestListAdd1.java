package clonegod.thread02.communication;

import java.util.ArrayList;
import java.util.List;

/**
 * t1 线程往list添加元素
 * t2 线程监测list中的元素，当size==5的时候，做某些操作 ---- t2主动查询变量的状态
 *
 */
public class TestListAdd1 {

	private volatile static List<String> list = new ArrayList<>();	
	
	public void add(){
		list.add("string");
	}
	public int size(){
		return list.size();
	}
	
	public static void main(String[] args) {
		
		final TestListAdd1 list1 = new TestListAdd1();
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					for(int i = 0; i <10; i++){
						list1.add();
						System.out.println("当前线程：" + Thread.currentThread().getName() + "添加了一个元素..");
						Thread.sleep(500);
					}	
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "t1");
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				// while循环不断查询，效率低
				while(true){
					if(list1.size() == 5){
						System.out.println("当前线程收到通知：" + Thread.currentThread().getName() + " list size = 5 线程停止..");
						throw new RuntimeException();
					}
				}
			}
		}, "t2");		
		
		t1.start();
		t2.start();
	}
	
	
}
