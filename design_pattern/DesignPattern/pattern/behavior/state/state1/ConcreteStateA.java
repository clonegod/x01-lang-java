package state1;

public class ConcreteStateA implements State {
	
	Context context;
	
	public ConcreteStateA(Context context) {
		this.context = context;
	}

	@Override
	public void handle() {
		System.out.println("ConcreteStateA handle this request");
		context.setCurrentState(context.getStateB());
	}

}
