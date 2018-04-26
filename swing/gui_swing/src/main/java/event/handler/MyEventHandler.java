package event.handler;

import event.MyButtonEvent;

public class MyEventHandler implements EventHandler {

	public void process(MyButtonEvent event) {
		System.out.println(this.getClass()+" process: " + event.toString());
	}

}