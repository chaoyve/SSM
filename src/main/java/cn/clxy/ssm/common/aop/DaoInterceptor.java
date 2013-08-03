package cn.clxy.ssm.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class DaoInterceptor {

	@Around("execution(* cn.clxy.ssm.*.dao.*.*(..))")
	public Object runOnAround(ProceedingJoinPoint point) throws Throwable {
		System.out.println("Start ====" + point.getTarget() + point.getArgs());
		Object object = point.proceed();
		System.out.println("End ====" + point.getSignature());
		return object;
	}
}
