package net.jcip02.safety;
import java.math.BigInteger;
import javax.servlet.*;

import net.jcip.annotations.*;

/**
 * UnsafeCountingFactorizer
 *
 * Servlet that counts requests without the necessary synchronization-------Servlet统计请求次数，对共享变量的操作未使用同步，出错
 *
 * @author Brian Goetz and Tim Peierls
 */
@NotThreadSafe
public class UnsafeCountingFactorizer extends GenericServlet implements Servlet {
    private long count = 0;

    public long getCount() {
        return count;
    }

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        /**
         * BAD
         * 由于Servlet是单实例的（Web容器只创建1次），但是运行在多线程环境下
         * 因此，将count作为共享数据时，必须使用同步机制才能保证得到正确的count值
         * */
        ++count; //竞态条件 1.读取 2.修改 3.写入 ：线程在每一步都可能失去cpu执行权，不是原子操作，所以不是线程安全的！
        encodeIntoResponse(resp, factors);
    }

    void encodeIntoResponse(ServletResponse res, BigInteger[] factors) {
    }

    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[] { i };
    }
}