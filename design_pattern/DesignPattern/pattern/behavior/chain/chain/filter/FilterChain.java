package chain.filter;

import chain.filter.filters.AuthenticationFilter;
import chain.filter.filters.EncryptionFilter;
import chain.filter.filters.Filter;
import chain.filter.filters.SecurityFilter;

/**
 * 构造过滤器链
 * 
 * @author Administrator
 *
 */
public class FilterChain {
	
	Filter filter;
	
	AuthenticationFilter authenticationFilter = new AuthenticationFilter();
	EncryptionFilter encryptionFilter = new EncryptionFilter();
	SecurityFilter securityFilter = new SecurityFilter();

	public FilterChain() {
		// initial first filter for the filter chain
		filter = encryptionFilter; 
		
		// build filter chain: 解密->认证->安全
		encryptionFilter.next(authenticationFilter)
						.next(securityFilter); 
	}
	
	
	public void doFilter(String request) {
		filter.doFilter(request);
	}
	
}
