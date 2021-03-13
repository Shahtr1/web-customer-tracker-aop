package com.luv2code.springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {

	// setup logger
	private Logger mylogger = Logger.getLogger(getClass().getName());
	
	// setup pointcut decalarations
	@Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	// do the same for service and dao
	@Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))")
	private void forDaoPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {}
	
	
	// add @Before advice
	@Before("forAppFlow()")
	public void before(JoinPoint theJoinPoint) {
		
		// display the method we are calling
		String theMethod = theJoinPoint.getSignature().toShortString();
		mylogger.info("=====>> in @Before: calling method: " + theMethod);
		
		// display the arguments to the method
		
		// get the arguments
		Object[] args = theJoinPoint.getArgs();
		
		for(Object temObject : args) {
			mylogger.info("=====>> argument: " + temObject);
		}
		
	}
	
	
	// add @AfterReturning advice
	@AfterReturning(
				pointcut = "forAppFlow()",
				returning = "theResult"
				)
	public void afterReturning(JoinPoint theJoinPoint, Object theResult) {
		// display the method we are calling
		String theMethod = theJoinPoint.getSignature().toShortString();
		mylogger.info("=====>> in @AfterReturning: from method: " + theMethod);
		
		// display data returned
		mylogger.info("=====>> result: " + theResult);
	}
	
}























