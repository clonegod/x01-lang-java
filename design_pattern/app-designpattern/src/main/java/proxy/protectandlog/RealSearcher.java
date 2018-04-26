package proxy.protectandlog;

public class RealSearcher implements Searcher {

	public String doSearch(String userId, String keyValue) {
		String sql = "SELECT * FROM data_table WHERE key_col = " + keyValue;
		// execute this SQL Statement
		return "doSearch return : result set...";
	}

}
