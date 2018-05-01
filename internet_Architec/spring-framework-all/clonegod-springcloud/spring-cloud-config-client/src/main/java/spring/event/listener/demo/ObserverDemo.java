package spring.event.listener.demo;

import java.util.Observable;
import java.util.Observer;

public class ObserverDemo {
	
	/**
	 * 使用jdk内置的观察者模式的实现 - 消息通知， 推模式
	 * 
	 * 推模式：订阅者被动接收消息
	 * 拉模式：客户端主动获取数据 - 比如，迭代器模式，就是客户端主动拉数据
	 */
	public static void main(String[] args) {
		MyObservable observable = new MyObservable();
		
		// 注册订阅者
		observable.addObserver(new Observer() {
			@Override
			public void update(Observable observable, Object message) {
				System.out.println("message: " + message);
			}
		});
		
		// 更新内部状态 - 主题发生更新，才会向订阅者发出通知
		observable.setChanged();
		
		// 向订阅者发出通知
		observable.notifyObservers("Hello,World");
	}
	
	
	private static class MyObservable extends Observable {

		/**
		 * setChanged方法是protected的，因此需要子类继承Observable后，才能调用到这个方法
		 */
		@Override
		public synchronized void setChanged() {
			super.setChanged();
		}
		
	}
}
