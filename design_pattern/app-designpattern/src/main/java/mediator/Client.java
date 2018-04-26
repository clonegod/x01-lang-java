package mediator;

public class Client {
	public static void main(String[] args) {
		Mediator mediator = new ConcretMediator();
		
		Colleague c1 = new ConcreteColleague1(mediator);
		Colleague c2 = new ConcreteColleague2(mediator);
		
//		mediator.addConcretColleague(c1);
//		mediator.addConcretColleague(c2);
		
		c2.change();
		
	}
}
