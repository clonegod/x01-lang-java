package observer.propertychange;

public class TestPropertyChangeSupport {
	public static void main(String[] args) {

		// 内部使用PropertyChangeSupport来提供Observable的功能
		MySourceBean myBean = new MySourceBean();

		// 在其它类中添加属性监听器
		new OtherListenerBean1(myBean);

		OtherListenerBean2 otherBean2 = new OtherListenerBean2();
		// 直接向源对象注册监听
		myBean.addPropertyChangeListener(otherBean2);

		// 当源内部的关心的属性发生变化时，发出通知
		myBean.setValue("value1");
		myBean.setValue("value2");
		myBean.setValue("value3");
	}
}
