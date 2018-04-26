package state1;

public class EndState implements State {
	
	Context context;
	
	public EndState(Context context) {
		this.context = context;
	}

	@Override
	public void handle() {
		System.out.println("EndState hanlde request~Over");
	}

}
