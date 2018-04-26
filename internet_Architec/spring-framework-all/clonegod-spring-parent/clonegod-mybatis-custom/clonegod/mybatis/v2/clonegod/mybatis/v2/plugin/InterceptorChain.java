package clonegod.mybatis.v2.plugin;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class InterceptorChain {

  private final List<Interceptor> interceptors = new LinkedList<Interceptor>();

  /**
   * java.lang.ClassCastException: com.sun.proxy.$Proxy3 cannot be cast to clonegod.mybatis.v2.statement.CGStatementHandler
   * 
   * 对代理对象进行二次代理时，需要找出真正的target对象
   * 
   */
  public Object pluginAll(Object target) {
    for (Interceptor interceptor : interceptors) {
      target = interceptor.plugin(target);
    }
    return target;
  }

  public void addInterceptor(Interceptor interceptor) {
    interceptors.add(interceptor);
  }
  
  public List<Interceptor> getInterceptors() {
    return Collections.unmodifiableList(interceptors);
  }

}