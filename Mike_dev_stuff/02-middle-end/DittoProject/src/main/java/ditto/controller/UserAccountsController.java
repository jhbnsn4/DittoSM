package ditto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ditto.model.UserAccounts;
import ditto.repo.UserAccountsDao;

@RestController //Makes this a bean
@RequestMapping("/users")
public class UserAccountsController {
	
	//Autowiring to the constructor
	private UserAccountsDao userDao;
	
	@GetMapping(value="/getAllUsers")
	public /*@ResponseBody*/ List<UserAccounts> getAllUsers(){
		return userDao.selectAll();
	}
	
	@PostMapping(value="/addUser")
	@ResponseStatus(value=HttpStatus.CREATED)
	public @ResponseBody String  addUser(@RequestBody UserAccounts newUser) {
		userDao.insert(newUser);
		return "Success";
	}
	
	//CONSTRUCTORS
	public UserAccountsController() {/* No Args */}
	
	@Autowired
	public UserAccountsController(UserAccountsDao userDao) {
		super();
		this.userDao = userDao;
	}

}
