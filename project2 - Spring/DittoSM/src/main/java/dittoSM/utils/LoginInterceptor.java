package dittoSM.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import dittoSM.model.UserAccount;

/**
 * Custom HandlerInterceptor that prevents users that have not logged in from accessing
 * most endpoints.
 *
 */
public class LoginInterceptor implements HandlerInterceptor {
	
	// Get access to the current session
	@Autowired
	private HttpSession session;

	// Whitelist of URIs that do not require a user to be logged in to access
	private final String URI_PREFIX = "/DittoSM/api/";
	private final String[] NO_LOGIN_CHECK_URI = {
			"userAccount/login",
			"userAccount/logout",
			"users/updateUserPassword",
			"users/addUser",
			"users/resetPassword",
			"users/addPicture",
			"users/updateUser",
			"posts/addLike",
			"users/getUserByIdPassword"
	};
	
	@Override
	public boolean preHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler) throws Exception {
		
		// Check if URI requires login
		for (String uri : NO_LOGIN_CHECK_URI) {
			// In our whitelist - does not require login
			if (request.getRequestURI().equals(URI_PREFIX + uri)) {
//				System.out.println(URI_PREFIX + uri);
//				System.out.println("in interceptor - whitelisted");
				return true;
			}
		}
		
		// Check session
		UserAccount user = (UserAccount) session.getAttribute("currentUser");
		if (user == null) {
			return false;
		}
		
		return true;
	}
	
}
