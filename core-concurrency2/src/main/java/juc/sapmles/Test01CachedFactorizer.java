package juc.sapmles;

import java.io.IOException;
import java.math.BigInteger;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class Test01CachedFactorizer implements Servlet {

	private BigInteger lastNumber;
	private BigInteger[] lastFactors;
	private long hits;
	private long cacheHits;

	
	public synchronized long getHits() {
		return hits;
	}
	
	public synchronized double getCacheHitRatio() {
		return (double)cacheHits / (double)hits;
	}
	
	/**
	 * Servlet-有状态的情况下，需要加锁对共享状态进行同步访问。
	 * 
	 * 凡是操作共享状态的代码片段，都需要加锁！而且要保证是同一个锁。
	 * 这里，同步代码块与同步方法，都使用了本类对象-this锁！
	 */
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		BigInteger i = extractFromRequest(req);
		BigInteger[] factors = null;
		synchronized (this) {
			++hits;
			if(i.equals(lastNumber)) {
				++cacheHits;
				factors = lastFactors.clone();
			}
		}
		if(factors == null) {
			factors = factor(i);
			synchronized (this) {
				lastNumber = i;
				lastFactors = factors.clone();
			}
		}
		encodeIntoResponse(res, factors);
	}

	private BigInteger[] factor(BigInteger i) {
		// TODO Auto-generated method stub
		return null;
	}

	private void encodeIntoResponse(ServletResponse res, BigInteger[] factors) {
		// TODO Auto-generated method stub
		
	}

	private BigInteger extractFromRequest(ServletRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	public void init(ServletConfig config) throws ServletException {
		
	}

	public ServletConfig getServletConfig() {
		return null;
	}

	public String getServletInfo() {
		return null;
	}

	public void destroy() {
		
	}


}
