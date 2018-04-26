package state1;

public class ConcreteStateB implements State {
	
	Context context;
	
	public ConcreteStateB(Context context) {
		this.context = context;
	}

	@Override
	public void handle() {
		System.out.println("ConcreteStateB handle this request");
		context.setCurrentState(context.getEndState());
	}

}
