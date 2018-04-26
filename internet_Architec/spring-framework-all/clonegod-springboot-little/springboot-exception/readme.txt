使用@ControllerAdvice对Controller抛出的异常进行全局捕获并合理转换异常。

1. 全局异常处理-自定义相关类型的异常类，再基于这些异常类分别进行处理。

@ControllerAdvice
@Order(999)
public class GlobalDefaultExceptionHanlder {
	...
}

- 普通Controller异常，可定制返回的页面，如404异常则返回到404.html，而不是显示404错误信息到页面上。
	异常处理方法需返回: ModelAndView

- Rest API 异常。可定制返回的JSON字符串内容。
	异常处理方法需返回: Json String

2. 针对某类Controller进行特殊异常处理，比如基于某个包下的所有Controller进行特殊处理。
@ControllerAdvice(basePackageClasses = {RestApi.class})
@Order(901)
public class RestApiControllerAdvice extends ResponseEntityExceptionHandler {
	...
}
	
3. 使用@Order注解，对不同的异常处理类进行排序，编号小的优先级高。
因此，同一个异常都满足RestApiControllerAdvice和GlobalDefaultExceptionHanlder的时候，将用RestApiControllerAdvice处理异常。