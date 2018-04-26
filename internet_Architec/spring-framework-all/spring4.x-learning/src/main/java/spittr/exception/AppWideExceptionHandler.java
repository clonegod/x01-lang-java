package spittr.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局统一处理所有控制器中处理器方法所抛出的异常！（从Spring 3.2开始）
 * 
 * 好处：
 * 	@ControllerAdvice最为实用的一个场景就是将所有的@ExceptionHandler方法收集到一个类中， 这样所有控制器的异常就能在一个地方进行一致的处理。
 * 	避免在多个控制器方法中重复相同的@ExceptionHandler方法处理异常。
 *  让业务方法（比如saveSpittle()）只关注正确的代码路径， 处理异常交给异常处理组件来完成。
 */
// 为控制器添加通知
@ControllerAdvice
public class AppWideExceptionHandler {
	
	/**
	 * 如果任意的控制器方法抛出了DuplicateSpittleException， 不管这个方法位于哪个控制器中， 都会调用这个duplicateSpittleHandler()方法来处理异常。
	 * @return 友好的错误页面
	 */
  @ExceptionHandler(DuplicateSpittleException.class)
  public String handleNotFound() {
	// Controller抛出异常，ViewNameInterceptor中的postHandle不会得到执行。无法添加逻辑视图前缀，所以手动设置前缀为thymeleaf。
    return "thymeleaf/error/duplicate"; 
  }

}
