package dittoSM.controller;

import java.io.IOException;
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

import dittoSM.model.ImageMap;
import dittoSM.model.UserAccount;
import dittoSM.model.UserAccountPackaged;
import dittoSM.service.ImageService;
import dittoSM.service.UserAccountService;
import dittoSM.utils.MyLogger;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UserAccountController {

	private UserAccountService userService;
	private ImageService imageService;

	@PostMapping(value = "/addUser")
	public void addUser(@RequestBody UserAccount user) {
		userService.addAccount(user);

//		return "added successfully...probably";
	}

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

	@GetMapping(value = "/getUserById", params = { "id" })
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

	@GetMapping(value = "/getCurrentUser")
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
		System.out.println("Image received: " + imageFile.getOriginalFilename());
		// Get current user

		// Retrieve the user from the current session
		UserAccount currentUser = (UserAccount) mySession.getAttribute("currentUser");

		// If there is no user in the session
		if (currentUser == null) {
			Logger log = MyLogger.getLoggerForClass(this);
			log.error("Session's current user is null");
			System.out.println("Session's user is null");
			return;
		}

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

	@GetMapping(value = "/getProfileImage", params = "userId")
	public ResponseEntity<byte[]> getProfileImage(@RequestParam("userId") Integer userId) {
		// Get the user
		UserAccount user = userService.getUserById(userId);
		
		// If we don't have a profile picture, use the default
		if (user.getProfilePicture() == null) {
			ImageMap defaultImg = imageService.getDefaultImage();
			if (defaultImg == null) {
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
	public UserAccountController(UserAccountService userService, ImageService imageService) {
		super();
		this.userService = userService;
		this.imageService = imageService;
	}

}
