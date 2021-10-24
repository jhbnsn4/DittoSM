package ditto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ditto.model.User;
import ditto.repo.UserDAO;

@Controller
@RequestMapping("user")
@CrossOrigin("http://localhost:4200")
public class UserController {
	private UserDAO userDao;
	
	@GetMapping(value="/test")
	public ResponseEntity<String> test() {
		return new ResponseEntity<String>("hello", HttpStatus.I_AM_A_TEAPOT);
	}
	
	@PostMapping(value="/signup")
	public @ResponseBody boolean createNewUser(@RequestBody User newUser) {
		
		return true;
	}
	
	
	//////////////constructors
	public UserController() {
	}
	@Autowired
	public UserController(UserDAO userDao) {
		super();
		this.userDao = userDao;
	}
	
}
