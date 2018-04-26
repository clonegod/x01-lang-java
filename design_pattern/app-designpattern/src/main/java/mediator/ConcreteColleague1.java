package mediator;

public class ConcreteColleague1 extends Colleague {

	public ConcreteColleague1(Mediator mediator) {
		super(mediator);
	}

	@Override
	public void action() {
		System.out.println("This is an action from Colleague 1");
	}

}
