package net.jcip02.safety;
import java.math.BigInteger;
import javax.servlet.*;

import net.jcip.annotations.*;

/**
 * CachedFactorizer
 * <p/>
 * Servlet that caches its last request and result
 *	当两个连续的请求对相同的数值进行因数分解时，可以直接使用上一次的计算结果。达到简单缓存的意图。
 *	并统计命中次数
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class CachedFactorizer extends GenericServlet implements Servlet {
    @GuardedBy("this") private BigInteger lastNumber;
    @GuardedBy("this") private BigInteger[] lastFactors;
    @GuardedBy("this") private long hits;
    @GuardedBy("this") private long cacheHits;

    public synchronized long getHits() {
        return hits;
    }

    public synchronized double getCacheHitRatio() {
        return (double) cacheHits / (double) hits;
    }

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = null; //栈内存变量，该变量由当前线程独占访问，不会在多线程中共享，因此不需要同步！

        /**只对共享变量进行同步*/
        synchronized (this) {
            ++hits; /**当已经使用了synchronized同步机制后，不要再使用AtomicLong，因为两种不同的同步机制可能会导致混乱*/
            if (i.equals(lastNumber)) {
                ++cacheHits;
                factors = lastFactors.clone();//赋值给栈内存变量
            }
        }
        if (factors == null) {
            factors = factor(i);
            /**只对共享变量进行同步*/
            synchronized (this) {
                lastNumber = i;
                lastFactors = factors.clone();//赋值给栈内存变量
            }
        }
        encodeIntoResponse(resp, factors);
    }

    void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
    }

    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[]{i};
    }
}