package bridge.driver;

public class MySqlDriver implements Driver {
	@Override
	public void createConn() {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		String method = String.format("class=%s, method=%s", ste.getClassName(), ste.getMethodName());
		System.out.println(method);
	}
}
