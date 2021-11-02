package dittoSM.controller;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.hash.Hashing;

import dittoSM.model.MyCustomMessage;
import dittoSM.model.UserAccount;
import dittoSM.model.UserAccountPackaged;
import dittoSM.service.UserAccountService;

/**
 * MK: Added session controller class to create login/logout endpoints.
 * 		Also creates and stores the currentUser for the current Http session.
 * 
 * Note: /api is coming from our dispatcher servlet URI pattern
 * @author mtkee
 */

@CrossOrigin(origins="#{environment.DITTO_ANGULAR_IP_AND_PORT}", allowCredentials = "true") //MK:Change port to match our Angular port
@RestController
@RequestMapping("/userAccount")
public class SessionController {

	private UserAccountService userService;
	
	//CONSTRUCTORS
	public SessionController() {/*No Args*/}

	@Autowired
	public SessionController(UserAccountService userService) {
		//All Args
		this.userService = userService;
	}
	
		
	//ENDPOINT METHODS
	
	// http://localhost:port/DittoSM/api/userAccount/login
	@PostMapping(value="/login")
	public MyCustomMessage login(HttpSession mySession, @RequestBody UserAccount incomingUser) {

		//select user by username
		UserAccount currentUser = userService.getUserByUsername(incomingUser.getUsername(), incomingUser.getUserEmail());
//		System.out.println(currentUser);
		//Encoding Password
		String hashedPassword = Hashing.sha256().hashString(incomingUser.getPassword(), StandardCharsets.UTF_8).toString();
		incomingUser.setPassword(hashedPassword);
//		System.out.println(currentUser);
		
		//Logic to check successful login
		
		//check password
		if(currentUser==null) 
		{
			mySession.invalidate();
			return new MyCustomMessage("Unsuccessfull login", "User Not Found");
		} else if(!incomingUser.getPassword().equals(currentUser.getPassword())) 
		{
			mySession.invalidate();
			return new MyCustomMessage("Unsuccessfull login", "Password Incorrect");
		} else 
		{
			mySession.setAttribute("currentUser", currentUser);
			return new MyCustomMessage("You have successfully logged IN", String.valueOf(currentUser.getUserId()));
		}
		
	}
	
	// http://localhost:port/DittoSM/api/userAccount/logout
	@PostMapping(value="/logout")
	public MyCustomMessage logout(HttpSession mySession) {

		mySession.invalidate();

		return new MyCustomMessage("You have successfully logged OUT", "");
	}

}
