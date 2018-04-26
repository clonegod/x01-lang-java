package mediator;

public abstract class Colleague {
	
	private Mediator mediator;
	
	public Colleague(Mediator mediator) {
		this.mediator = mediator;
		mediator.addConcretColleague(this);
	}
	
	public abstract void action();
	
	// 事件源
	public void change() {
		mediator.colleagueChanged(this);
	}
}
