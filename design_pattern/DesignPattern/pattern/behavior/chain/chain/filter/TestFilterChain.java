package chain.filter;

public class TestFilterChain {
	public static void main(String[] args) {
	      FilterManager filterManager = new FilterManager(new Target());
	      
	      Client client = new Client();
	      client.setFilterManager(filterManager);
	      client.sendRequest("LOGIN");
	   }
}
