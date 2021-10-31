package dittoSM.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.hash.Hashing;

import dittoSM.EmailService;
import dittoSM.model.MyCustomMessage;
import dittoSM.model.UserAccount;
import dittoSM.model.UserAccountPackaged;
import dittoSM.service.UserAccountService;
import dittoSM.utils.MyLogger;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins="http://localhost:4200", allowCredentials="true")
public class UserAccountController {
	
	private UserAccountService userService;
	
	private EmailService mailer;
	
	@PostMapping(value="/addUser")
	public void addUser(@RequestBody UserAccount user) {
		userService.addAccount(user);
	
//		return "added successfully...probably";
	}
	
	@PutMapping(value="/updateUser")
	public void updateUser(HttpSession mySession, @RequestBody UserAccountPackaged userPackaged) {
		
		// Get actual user from DB
		UserAccount user = userService.getUserById(userPackaged.getUserId());
		
		// Don't update if account could not be found
		if (user == null) {
			Logger log = MyLogger.getLoggerForClass(this);
			log.error("Attempting to update invalid account of id: " + userPackaged.getUserId());
			return;
		}
		
		// Change packaged fields
		user.setFirstName(userPackaged.getFirstName());
		user.setLastName(userPackaged.getLastName());
		user.setBirthday(userPackaged.getBirthday());
		user.setStatusText(userPackaged.getStatusText());
		
		// Update session
		mySession.setAttribute("currentUser", user);
		
		// Update record in DB
		userService.updateAccount(user);
		
//		return "updated account";
	}
	
//////////////////////////////////FOR UPDATE PASSWORD/////////////////////////////////////////////////////
	@PutMapping(value="/updateUserPassword")
	public MyCustomMessage updateUserPassword(HttpSession mySession, @RequestBody UserAccount userAccount) {
		
		// Get actual user from DB
		UserAccount user = userService.getUserById(userAccount.getUserId());
		
		// Don't update if account could not be found
		if (user == null) {
			Logger log = MyLogger.getLoggerForClass(this);
			log.error("Attempting to update invalid account: " + userAccount.getUserId());
			return new MyCustomMessage("Invalid Account","");
		}
		
		// Change Password
		String hashedPassword = Hashing.sha256().hashString(userAccount.getPassword(), StandardCharsets.UTF_8).toString();
		user.setPassword(hashedPassword);
		
		// Update record in DB
		userService.updateAccount(user);
		return new MyCustomMessage("Password Succefully Updated","");
		
	}
	
	@PostMapping(value="/resetPassword")
	public MyCustomMessage resetPassword(HttpSession mySession, @RequestBody String email) {
		
		//Get User from DB
		UserAccount currentUser = userService.getUserByUsername("", email);
		//Check if user or not
		if(currentUser==null) 
		{
			return new MyCustomMessage("Invalid Email Address", "Please Try Again");
		} else 
		{
			//Prepare message
			int userId = currentUser.getUserId();
			String url = "http://localhost:4200/reset/";
			String message = "Click the following link to reset your password: "+url+userId;
			mailer.sendMail(email, "Password Reset", message);
			return new MyCustomMessage("Message has been sent to:", email);
		}
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////	
	@GetMapping(value="/getUserById", params= {"id"})
	public UserAccountPackaged getUserById(int id) {
		// Find the user in the DB
		UserAccount user = userService.getUserById(id);
		
		// Check if we found an actual user
		if (user == null) {
			Logger log = MyLogger.getLoggerForClass(this);
			log.error("No account found with id: " + id);
			return null;
		}
		
		// Respond with only the information we want to send 
		return new UserAccountPackaged(user);
	}
	
	@GetMapping(value="/getCurrentUser")
	public UserAccountPackaged getCurrentUser(HttpSession mySession) {
		// Retrieve the user from the current session
		UserAccount currentUser = (UserAccount) mySession.getAttribute("currentUser");
		
		// If there is no user in the session
		if (currentUser == null) {
			Logger log = MyLogger.getLoggerForClass(this);
			log.error("Session's current user is null");
			return null;
		}
		
		// Respond with only the information we want to send
		return new UserAccountPackaged(currentUser);
	}
	
	@GetMapping(value="/getAllUsers")
	public List<UserAccount> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping(value="/getUserByUsername", params= {"username"})
    public UserAccount getUserByUsername(String username, String email) {
        return userService.getUserByUsername(username, email);
    }
	
////////////////// CONSTRUCTORS
	
	public UserAccountController() {
	}

	@Autowired
	public UserAccountController(UserAccountService userService, EmailService mailer) {
		super();
		this.userService = userService;
		this.mailer = mailer;
	}
	
}
