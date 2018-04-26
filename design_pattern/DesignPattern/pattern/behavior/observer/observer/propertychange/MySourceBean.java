package observer.propertychange;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MySourceBean {

	/**
	 * 当Java Bean 内部的属性发生改变时，所有向PropertyChangeSupport注册的监听器对象都将接收到与此更新相关的事件- PropertyChangeEvent
	 */
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

	private String value;

	public String getValue() {
		return this.value;
	}

	public void setValue(String newValue) {
		String oldValue = this.value;
		this.value = newValue;
		this.pcs.firePropertyChange("value", oldValue, newValue);
	}

}
