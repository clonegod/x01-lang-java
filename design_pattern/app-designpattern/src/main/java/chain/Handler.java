package chain;

public abstract class Handler {
	
	// 下家
	protected Handler successor;
	
	public abstract void handleRequest();

	public Handler getSuccessor() {
		return successor;
	}

	public void setSuccessor(Handler successor) {
		this.successor = successor;
	}
	
	public String nextName() {
		return getSuccessor().getClass().getName();
	}
	
	
}
