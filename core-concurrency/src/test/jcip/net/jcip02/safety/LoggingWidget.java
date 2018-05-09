package net.jcip02.safety;

public class LoggingWidget extends Widget {

	@Override
	public synchronized void dosomething() {
		System.out.println("child:this="+this);
		super.dosomething();//调用父类的方法，此时的this为子类对象（运行时动态绑定）。哪个对象调用本方法，this即为哪个对象！
	}
	
	
	public static void main(String[] args) {
		//测试可重入锁: 1个线程对同一个锁，可以获取多次
		Widget widget = new LoggingWidget();
		widget.dosomething();
		//output:
		//child: this=net.jcip02.safety.LoggingWidget@245f4ae
		//parent: this=net.jcip02.safety.LoggingWidget@245f4ae
		
		widget = new Widget();
		widget.dosomething();
		//parent: this=net.jcip02.safety.Widget@3ec2ccac
		
	}
}
