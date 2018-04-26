package chain.filter;

public class Target {
	
	public void doSomethingAfterFilterChain(String request) {
		System.out.println("Target process request: " + request);
	}
	
}
