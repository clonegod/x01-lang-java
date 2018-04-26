package chain;


public class ConcretHandler3 extends Handler {
	

	@Override
	public void handleRequest() {
		if (getSuccessor() != null) {
			System.out.println("The request is passed to " + nextName());
			getSuccessor().handleRequest();
		} else {
			System.out.println("The request is handled here");
		}
	}

}
