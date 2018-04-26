package chain.filter.filters;

public class SecurityFilter extends AbstractFilter {

	public boolean execute(String request) {
		System.out.println("SecurityFilter request: " + request);
		return true;
	}

}
