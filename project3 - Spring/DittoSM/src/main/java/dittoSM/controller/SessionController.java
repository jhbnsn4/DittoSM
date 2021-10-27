package dittoSM.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dittoSM.dao.UserAccountDao;
import dittoSM.model.MyCustomMessage;
import dittoSM.model.UserAccount;

/**
 * MK: Added session controller class to create login/logout endpoints.
 * 		Also creates and stores the currentUser for the current Http session.
 * 
 * Note: /api is coming from our dispatcher servlet URI pattern
 * @author mtkee
 */

@CrossOrigin(origins="http://localhost:4200", allowCredentials = "true") //MK:Change port to match our Angular port
@RestController
@RequestMapping("/userAccount")
public class SessionController {

	private UserAccountDao userAccountDao;
	
	//CONSTRUCTORS
	public SessionController() {/*No Args*/}

	@Autowired
	public SessionController(UserAccountDao userAccountDao) {
		//All Args
		this.userAccountDao = userAccountDao;
	}
	
		
	//ENDPOINT METHODS
	
	// http://localhost:port/DittoSM/api/userAccount/
	@GetMapping(value="/")
	public UserAccount getAllUserAccounts(HttpSession mySession){

		UserAccount currentUser = (UserAccount) mySession.getAttribute("currentUser");
		
		if(currentUser != null)
			return currentUser;
		else
			return new UserAccount();
	}
	
	// http://localhost:port/DittoSM/api/userAccount/login
	@PostMapping(value="/login")
	public MyCustomMessage login(HttpSession mySession, @RequestBody UserAccount incomingUser) {
		
		UserAccount currentUser = userAccountDao.selectUserById(incomingUser.getUserId());
		System.out.println(currentUser);
		
		//Logic to check successful login
		if(currentUser==null) {
			
			return new MyCustomMessage("Unsuccessfull login", "Please try again");
		}
		mySession.setAttribute("currentUser", currentUser);
		
		//MK: Need to add log4j to track successful login
		return new MyCustomMessage("You have successfully logged IN", currentUser.getUsername());
	}
	
	// http://localhost:port/DittoSM/api/userAccount/logout
	@PostMapping(value="/logout")
	public MyCustomMessage addNewUserAccount(HttpSession mySession) {

		mySession.invalidate();
		
		return new MyCustomMessage("You have successfully logged OUT", "");
	}

}
