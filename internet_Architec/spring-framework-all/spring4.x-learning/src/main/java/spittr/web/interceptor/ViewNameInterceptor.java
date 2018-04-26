package spittr.web.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 提供JSP和Thymeleaf混合视图的支持，动态设置逻辑视图的前缀来标识资源，以便在对应ViewResolver中的viewNames属性进行匹配过滤！
 *
 */
public class ViewNameInterceptor extends HandlerInterceptorAdapter {

	Logger logger = LoggerFactory.getLogger(ViewNameInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.debug("---> preHandle: this method run before handler");
		
		return super.preHandle(request, response, handler);
	}

	/**
	 * handler返回ModelAndView之后，修改View所执行的逻辑视图名称：添加前缀jsp或者thymeleaf
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if(modelAndView == null) {
			// restful api, no necessary to set view prefix
		} else {
			addViewNamePrefix(modelAndView);
		}
	}

	private void addViewNamePrefix(ModelAndView modelAndView) {
		logger.debug("---> postHandle: this method run after handler, before ViewResolver");
		
		String originalViewName = modelAndView.getViewName();
		String viewNamePrefix = Math.random() > 0.5 ? "jsp" : "thymeleaf";
		
		// 重定向 和 BeanNameView 类型的逻辑视图名称，是特殊视图，不进行修改。
		boolean passby = originalViewName.startsWith("redirect") || originalViewName.endsWith("View");
		if(! passby) {
			modelAndView.setViewName(viewNamePrefix+"/"+originalViewName);
		}
	}

	/**
	 * 视图渲染完成，或者抛出异常时
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.debug("---> afterCompletion: this method run after ViewResolver");
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterConcurrentHandlingStarted(request, response, handler);
	}
	
	
} 