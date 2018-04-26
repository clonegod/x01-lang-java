package chain.filter.filters;

public class AuthenticationFilter extends AbstractFilter {
	
	public boolean execute(String request) {
		System.out.println("Authenticating request: " + request);
		return true;
	}

}
