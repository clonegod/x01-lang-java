package observer.propertychange;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class OtherListenerBean1 {

	MySourceBean mybean;

	public OtherListenerBean1(MySourceBean mybean) {
		this.mybean = mybean;

		// 注册一个mybean属性的监听器，当mybean的属性更新时，会得到通知
		mybean.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				handlePropChange(evt);
			}
		});
	}

	public void handlePropChange(PropertyChangeEvent evt) {
		System.out.println(String.format("class=%s, source=%s, old=%s, new=%s", this.getClass(),
				evt.getSource().getClass(), evt.getOldValue(), evt.getNewValue()));
	}

}
