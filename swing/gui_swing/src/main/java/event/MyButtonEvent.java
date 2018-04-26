package event;

import event.listener.MyButttonEventListenerAdapter;
import event.listener.MyEventListener;

/**
 * 事件：每个事件都有自己的特定事件属性，以及其它一些共性的事件属性
 *
 */
public class MyButtonEvent /*extends MyEvent*/{
	
	private String evtNo; // 事件编号
	private String evtSrc; // 事件发生的源
	
	public MyButtonEvent(String evtNo, String evtSrc) {
		super();
		this.evtNo = evtNo;
		this.evtSrc = evtSrc;
	}

	MyButttonEventListenerAdapter myBtnEventListener;
	
	public void addEventListener(MyEventListener mel) {
		this.myBtnEventListener = (MyButttonEventListenerAdapter)mel;
	}
	
	public void click() {
		myBtnEventListener.onClick(this);
	}
	
	public void dbClick() {
		myBtnEventListener.onDoubleClick(this);
	}
	
	public void action() {
		// donothing
	}
	
	
	@Override
	public String toString() {
		return "MyButtonEvent [evtNo=" + evtNo + ", evtSrc=" + evtSrc + "]";
	}

	// getters & setters
	public String getEvtNo() {
		return evtNo;
	}

	public void setEvtNo(String evtNo) {
		this.evtNo = evtNo;
	}

	public String getEvtSrc() {
		return evtSrc;
	}

	public void setEvtSrc(String evtSrc) {
		this.evtSrc = evtSrc;
	}
	
}