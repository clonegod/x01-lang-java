package chain.filter.filters;

public class EncryptionFilter extends AbstractFilter {

	public boolean execute(String request) {
		System.out.println("EncryptionFilter request: " + request);
		return true;
	}

}
