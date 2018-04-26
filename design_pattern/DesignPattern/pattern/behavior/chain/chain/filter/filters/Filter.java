package chain.filter.filters;

public interface Filter {
	
	/**
	 * 执行filter过滤
	 * 
	 * @param request
	 * @return
	 */
	public void doFilter(String request);
	
	public Filter next(Filter filter);
	
}
