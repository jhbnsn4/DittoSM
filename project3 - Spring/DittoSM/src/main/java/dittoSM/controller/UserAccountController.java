package dittoSM.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dittoSM.model.UserAccount;
import dittoSM.service.UserAccountService;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins="http://localhost:4200", allowCredentials="true")
public class UserAccountController {
	
	private UserAccountService userService;
	
	@GetMapping(value="/getAllUsers")
	public List<UserAccount> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@PostMapping(value="/addUser")
	public String addUser(@RequestBody UserAccount user) {
		userService.addAccount(user);
	
		return "added successfully...probably";
	}
	
	@PutMapping(value="/updateUser")
	public String updateUser(@RequestBody UserAccount user) {
		userService.updateAccount(user);
		
		return "updated account";
	}
	
	
////////////////// CONSTRUCTORS
	
	public UserAccountController() {
	}

	@Autowired
	public UserAccountController(UserAccountService userService) {
		super();
		this.userService = userService;
	}
	
}
