package net.jcip02.safety;
import java.math.BigInteger;
import javax.servlet.*;

import net.jcip.annotations.*;

/**
 * SynchronizedFactorizer
 *
 * Servlet that caches last result, but with unnacceptably poor concurrency
 *
 * @author Brian Goetz and Tim Peierls
 */

@ThreadSafe
public class SynchronizedFactorizer extends GenericServlet implements Servlet {
    @GuardedBy("this") private BigInteger lastNumber;
    @GuardedBy("this") private BigInteger[] lastFactors;

    //虽然加入同步确保了线程安全问题，但是做法极端：在整个service()方法上加锁，导致===》性能问题：service不再有任何并发性
    public synchronized void service(ServletRequest req,
                                     ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        if (i.equals(lastNumber))
            encodeIntoResponse(resp, lastFactors);
        else {
            BigInteger[] factors = factor(i);
            lastNumber = i;
            lastFactors = factors;
            encodeIntoResponse(resp, factors);
        }
    }

    void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
    }

    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[] { i };
    }
}