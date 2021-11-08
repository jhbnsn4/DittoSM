package dittoSM.utils;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import dittoSM.model.UserAccount;

/**
 * Aspect for logging thrown exceptions and other notable events
 *
 */
@Component("exceptionLoggingAspect")
@Aspect
public class CustomLoggingAspect {

	private Logger log = MyLogger.getLoggerForClass(this);

	// Log any exception thrown from within our application
	@AfterThrowing(pointcut = "execution(* dittoSM..*(..)) && !execution(* dittoSM.utils..*(..))", throwing = "exc")
	public void afterTaskThrow(JoinPoint jp, Exception exc) {
		log.error("Encountered exception:", exc);
	}

	// Log method calls in SessionController
	@After("execution(* dittoSM.controller.SessionController..*(..))")
	public void afterSessionController(JoinPoint jp) {
		
		// Get session from args
		HttpSession mySession = null; 
		try {
			mySession = (HttpSession)jp.getArgs()[0];
		} catch (ClassCastException e) {
			log.error(e);
			return;
		}
		
		// Log user
		try {
			UserAccount currentUser = (UserAccount) mySession.getAttribute("currentUser");
			log.info("User " + currentUser.getUsername() + " triggered session method: " + jp.getSignature());
		} catch (Exception e) {
			log.error(e);
		}
	}

	// Log when new user registers
	@After("execution(* dittoSM.controller.UserAccountController.addUser(..))")
	public void afterAddUser(JoinPoint jp) {
		// Get user from args
		UserAccount user = (UserAccount) jp.getArgs()[0];
		if (user == null) {
			log.error("Attempted to register invalid user object");;
		}
		
		log.info("User " + user.getUsername() + " has registered an account.");
	}

}
