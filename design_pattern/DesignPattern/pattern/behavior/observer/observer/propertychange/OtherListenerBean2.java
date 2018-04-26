package observer.propertychange;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class OtherListenerBean2 implements PropertyChangeListener {

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		handlePropChange(evt);
	}

	public void handlePropChange(PropertyChangeEvent evt) {
		System.out.println(String.format("class=%s, source=%s, old=%s, new=%s", this.getClass(),
				evt.getSource().getClass(), evt.getOldValue(), evt.getNewValue()));
	}

}
