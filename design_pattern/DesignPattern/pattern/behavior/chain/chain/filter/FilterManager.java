package chain.filter;

/**
 * 封装filterChain，与目标对象
 * 
 * @author Administrator
 *
 */
public class FilterManager {
	
   FilterChain filterChain;
   Target target;
   
   public FilterManager(Target target){
      filterChain = new FilterChain();
      this.target = target;
   }

   public void filterRequest(String request){
      filterChain.doFilter(request);
      
      // 所有过滤链执行完成后，根据具体情况决定是否继续执行"目标对象"的方法。
      target.doSomethingAfterFilterChain(request);
   }
}