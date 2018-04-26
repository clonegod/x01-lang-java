package proxy.protectandlog;

/**
 * 安全代理
 *
 */
public class SearcherProxy implements Searcher {
	private RealSearcher searcher; //被代理的对象
	private UsageLogger usageLogger;
	private AccessValidator accessValidator;
	
	public SearcherProxy() {
		this.searcher = new RealSearcher();
		this.accessValidator = new AccessValidator();
		this.usageLogger = new UsageLogger();
	}
	
	public String doSearch(String userId, String keyValue) {
		//before- checkAccess
		if(checkAccess(userId)) {
			String result = searcher.doSearch(userId, keyValue); // 被代理对象在此处真正得到执行
			logUsage(userId);// after- logUsage
			return result;
		}
		return null;
	}

	private void logUsage(String userId) {
		usageLogger.setUserId(userId);
		usageLogger.save();
	}

	private boolean checkAccess(String userId) {
		return accessValidator.validateUser(userId);
	}
}
