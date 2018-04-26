package proxy.protectandlog;

public class Client {
	
	private static Searcher searcher; 
	
	public static void main(String[] args) {
		searcher = new SearcherProxy();
		
		String userId = "Admin";
		String searchType = "SEARCH_BY_ACCOUNT_NUMBER";
		String result = 
				searcher.doSearch(userId, searchType);
		
		System.out.println(result);
		
	}
}
