package state1;

public class StartState implements State {
	
	Context context;
	
	public StartState(Context context) {
		this.context = context;
	}

	@Override
	public void handle() {
		System.out.println("StartState hanlde request");
		context.setCurrentState(context.getStateA());
	}

}
