package spittr.web.aspect;

import java.io.IOException;
import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import spittr.exception.MethodArgumentNotValidException;

@Aspect
@Component
public class SpittrApiAspect {
	
	Logger logger = LoggerFactory.getLogger(SpittrApiAspect.class);
	
	public SpittrApiAspect() {
		System.out.println();
	}
	
	// 提供切面定义，然后再@Before,@After中通过方法名引用该切面。
	@Pointcut("execution(* spittr.web.api..*(..))")
	public void apiPonitCut() {}
	
	
	@Around("apiPonitCut()")
	public Object around(ProceedingJoinPoint proceedingJP) throws Throwable {
		Object retVal = null;
		Throwable t = null;
		
		Object[] reqArgs = proceedingJP.getArgs();
		logger.info("method={}, args={}", 
				proceedingJP.getSignature().getName(), Arrays.toString(reqArgs));
		
		// 加入重试逻辑
		for(int n = 0; n < 3; n++) {
			try {
				retVal = proceedingJP.proceed(reqArgs);
				t = null;
				break;
			} catch (Throwable e) {
				if(e instanceof IOException) {
					t = e;
					continue; // IO异常进行重试
				} else {
					throw e; // 其它异常直接抛出
				}
			}
		}
		if(t != null) {
			throw t;
		}
		return retVal;
	}
	
	
	//@Before("apiPonitCut()")
	public void before(JoinPoint joinPoint) {
		System.out.println(joinPoint.getArgs());
	}
	
	//@AfterReturning("apiPonitCut()")
	public void afterReturn(JoinPoint joinPoint) {
		System.out.println(joinPoint.getArgs());
	}
	
	@AfterThrowing(pointcut="apiPonitCut()", throwing="apiException")
	public void handleError(JoinPoint joinPoint, MethodArgumentNotValidException apiException) {
		logger.error("--------------");
		logger.error("method:{}, exception:{}", joinPoint.getSignature().getName(), apiException.getMessage());
		logger.error("--------------");
	}
	
	
}
