package chain.filter.filters;

public abstract class AbstractFilter implements Filter {
	
	public Filter next;

	@Override
	public void doFilter(String request) {
		boolean doNext = execute(request);
		if(doNext && next != null) {
			next.doFilter(request); // 递归doFilter
		}
	}
	
	@Override
	public Filter next(Filter filter) {
		this.next = filter;
		return next;
	}

	protected abstract boolean execute(String request);
}
