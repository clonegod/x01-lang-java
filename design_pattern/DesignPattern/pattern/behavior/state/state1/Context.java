package state1;

public class Context {
	
	private State startState;
	private State stateA;
	private State stateB;
	private State endState;
	
	public Context() {
		startState = new StartState(this);
		stateA = new ConcreteStateA(this);
		stateB = new ConcreteStateB(this);
		endState = new EndState(this);
		currentState = startState;
	}
	
	private State currentState;

	public void handle() {
		currentState.handle();
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public State getStartState() {
		return startState;
	}

	public State getEndState() {
		return endState;
	}

	public State getStateA() {
		return stateA;
	}

	public State getStateB() {
		return stateB;
	}
}
