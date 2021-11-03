package dittoSM.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.hash.Hashing;

import dittoSM.EmailService;
import dittoSM.model.ImageMap;
import dittoSM.model.MyCustomMessage;
import dittoSM.model.UserAccount;
import dittoSM.model.UserAccountPackaged;
import dittoSM.service.ImageService;
import dittoSM.service.UserAccountService;
import dittoSM.utils.MyLogger;
/** 
 * @author Ryan Moss
 */
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "#{environment.DITTO_ANGULAR_IP_AND_PORT}", allowCredentials = "true")
public class UserAccountController {

	private UserAccountService userService;
	private EmailService mailer;
	private ImageService imageService;

	/**
	 * Adds a user to the database.
	 * Takes a current user from the request body and uses the service layer to add a user.
	 * @param user is the user information being sent.
	 */
	@PostMapping(value = "/addUser")
	public void addUser(@RequestBody UserAccount user) {
		userService.addAccount(user);
//		return "added successfully...probably";
	}

	/**
	 * This method updates the user passed through the request body, checks for a null object, and updates the
	 * @param mySession is an HTTP session that is updated after the UserAccount is updated.
	 * @param userPackaged UserPackaged is a packaged form of UserAccount that returns/sends necessary (not sensitive) User information.
	 */
	@PutMapping(value = "/updateUser")
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
	
	/**
	 * Returns a custom message and updates the User Password, after an email is sent.
	 * @param userAccount is passed in the Request Body and is checked for a null value.  
	 * @return returns a custom message.  
	 */
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
	
	/**
	 * Reset Password acquires a user, checks for null value, and returns a custom message based on success/failure.
	 * @param mySession
	 * @param email is a string acquired from the request body and used 
	 * to email/update the current users' password.
	 * @return MyCustomMessage is returned based on user input.
	 */
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
			String url = System.getenv("DITTO_ANGULAR_IP_AND_PORT") + "/#/reset/";
			String message = "Click the following link to reset your password: "+url+userId;
			mailer.sendMail(email, "Password Reset", message);
			return new MyCustomMessage("Message has been sent to:", email);
		}
	}
	
	/**
	 * Retrieves a user by id, via "params" -- checks for null value, and returns a 
	 * "packaged" form of our user object.
	 * @param id = user id
	 * @return this method returns a packaged (a user object without sensitive information attached)
	 */
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
	
	/**
	 * This method acquires a user via it's id, and returns the full user.  
	 * @param id userId
	 * @return returns an "un-packaged" user.
	 */
	@GetMapping(value="/getUserByIdPassword", params= {"id"})
	public UserAccount getUserByIdPassword(int id) {
		// Find the user in the DB
		UserAccount user = userService.getUserById(id);
		
		// Check if we found an actual user
		if (user == null) {
			Logger log = MyLogger.getLoggerForClass(this);
			log.error("No account found with id: " + id);
			return null;
		}
		
		return user;
	}

	/**
	 * Via the current session, this method acquires the current user, which is
	 * set to "currentUser" at login.
	 * @param mySession current HTTPSession
	 * @return returns a "UserAccountPackaged" a information-sensitive-safe version of a UserAccount.
	 */
	@GetMapping(value = "/getCurrentUser")
	public UserAccountPackaged getCurrentUser(HttpSession mySession) {
		// Retrieve the user from the current session
		UserAccount currentUser = (UserAccount) mySession.getAttribute("currentUser");

		// Respond with only the information we want to send
		return new UserAccountPackaged(currentUser);
	}
	/**
	 * Returns a list of All Users.
	 * @return a List of UserAccounts
	 */
	@GetMapping(value = "/getAllUsers")
	public List<UserAccount> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping(value = "/getUserByUsername", params = { "username" })
	public UserAccount getUserByUsername(String username, String email) {
		return userService.getUserByUsername(username, email);
	}

	@PostMapping(value = "/addProfilePicture")
	public void addProfilePicture(HttpSession mySession, @RequestParam("imageFile") MultipartFile imageFile) {
		// Get current user

		// Retrieve the user from the current session
		UserAccount currentUser = (UserAccount) mySession.getAttribute("currentUser");

		// Save the image to our database
		boolean newProfile = imageService.addProfilePicture(imageFile, currentUser.getProfilePicture());

		// If we added a NEW profile image
		if (newProfile) {
			// Update user with new image name
			currentUser.setProfilePicture(imageFile.getOriginalFilename());
			mySession.setAttribute("currentUser", currentUser);
			userService.updateAccount(currentUser);
		}

	}
	/**
	 * Acquires the User via a userId, checks for the null value of the profile picture,
	 * if null, a default picture is assigned.  
	 * @param userId the id of user passed via "params"
	 * @return Returns a JPEG image based on the current state of the UserAccount.
	 */
	@GetMapping(value = "/getProfileImage", params = "userId")
	public ResponseEntity<byte[]> getProfileImage(@RequestParam("userId") Integer userId) {
		// Get the user
		UserAccount user = userService.getUserById(userId);
		
		// If we don't have a profile picture, use the default
		if (user.getProfilePicture() == null) {
			ImageMap defaultImg = imageService.getDefaultImage();
			if (defaultImg == null) {
				Logger log = MyLogger.getLoggerForClass(this);
				log.warn("No default image found!");
				System.out.println("No default image!");
				return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(null);
			}
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(defaultImg.getImageFile());
		}

		// Retrieve the image file
		byte[] imageByteArray = {};
		if (user != null && user.getProfilePicture() != null) {
			imageByteArray = imageService.getImageByName(user.getProfilePicture()).getImageFile();
		}

		// Send as Blob
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageByteArray);
	}
	/**
	 * Adds a picture via an "imageFile"
	 * @param imageFile the image file passed via @RequestParam.
	 */
	@PostMapping(value = "/addPicture")
	public void addProfilePicture(@RequestParam("imageFile") MultipartFile imageFile) {

		// Read the new image file
		byte[] imageBytes = {};
		try {
			imageBytes = imageFile.getBytes();
		} catch (IOException e) {
			Logger log = MyLogger.getLoggerForClass(this);
			log.error("Exception when reading image file", e);
			e.printStackTrace();
			return;
		}

		imageService.addImage(new ImageMap(imageBytes, imageFile.getOriginalFilename()));
	}

////////////////// CONSTRUCTORS

	public UserAccountController() {
	}

	@Autowired
	public UserAccountController(UserAccountService userService, EmailService mailer, ImageService imageService) {
		super();
		this.userService = userService;
		this.mailer = mailer;
		this.imageService = imageService;
	}

}
