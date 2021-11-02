package dittoSM.utils;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging any exceptions thrown in our application
 *
 */
@Component("exceptionLoggingAspect")
@Aspect
public class ExceptionLoggingAspect {
	
	
	@AfterThrowing(pointcut="execution(* dittoSM..*(..)) && !execution(* dittoSM.utils..*(..))", throwing="exc")
	public void afterTaskThrow(JoinPoint jp, Exception exc) {
		Logger log = MyLogger.getLoggerForClass(this);
		log.error("Encountered exception:", exc);
	}
}
