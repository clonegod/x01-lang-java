package guava.event;

public class LogInfoEvent implements Event {

	private String log;
	
	public LogInfoEvent(String log) {
		this.log = log;
	}

	@Override
	public Object getEvent() {
		return this.log;
	}

	@Override
	public String toString() {
		return "LogEvent [log=" + log + "]";
	}

}
