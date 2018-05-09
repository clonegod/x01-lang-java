package net.jcip02.safety;
import java.math.BigInteger;
import java.util.concurrent.atomic.*;
import javax.servlet.*;

import net.jcip.annotations.*;

/**
 * UnsafeCachingFactorizer
 *
 * Servlet that attempts to cache its last result without adequate atomicity
 *
 * 此类原本的意图: 当两个连续的请求对相同的数值进行因数分解时，可以直接使用上一次的计算结果。达到简单缓存的意图。
 * 
 * @author Brian Goetz and Tim Peierls
 */

@NotThreadSafe
public class UnsafeCachingFactorizer extends GenericServlet implements Servlet {
    private final AtomicReference<BigInteger> lastNumber
            = new AtomicReference<BigInteger>();
    private final AtomicReference<BigInteger[]> lastFactors
            = new AtomicReference<BigInteger[]>();

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        /**
         * 虽然AtomicReference对对象的是原子性引用
         * 但是，当多个线程安全类之前存在竞态条件时，就不再是线程安全的！
         * 因为，AtomicReferenceA 只能保证自身的原子操作，并不能同步更新AtomicReferenceB
         * 当程序逻辑同时依赖于它们时，就会出现问题
         */
        BigInteger[] factors = null;
        if (i.equals(lastNumber.get()))
        	factors = lastFactors.get();
        else {
            factors = factor(i);
            lastNumber.set(i);
            lastFactors.set(factors);
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
