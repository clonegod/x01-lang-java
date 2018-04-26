package chain.version2;

public abstract class AbstractFilter implements Filter {

	Filter next;
	
	public boolean doFilter(Object... arg) {
		boolean matched = filter();
		if(matched || next==null) {
			return matched;
		}
		return next.doFilter(arg);
	}
	
	abstract boolean filter(Object...args);

}
