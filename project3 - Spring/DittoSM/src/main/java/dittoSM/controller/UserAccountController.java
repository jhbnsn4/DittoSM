package dittoSM.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dittoSM.dao.UserAccountDao;
import dittoSM.model.UserAccount;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins="http://localhost:4200", allowCredentials="true")
public class UserAccountController {
	
	// TODO: Need to set up service layer
	private UserAccountDao userDao;
	
	@GetMapping(value="/getAllUsers")
	public List<UserAccount> getAllUsers() {
		return userDao.selectAllUsers();
	}
	
	@PostMapping(value="/addUser")
	public void addUser(@RequestBody UserAccount user) {
		userDao.insertAccount(user);
		System.out.println("Added Successfully");
//		return "added successfully...probably";
	}
	
	@GetMapping(value="/getUserById", params= {"id"})
    public UserAccount getUserById(int id) {
        return userDao.selectUserById(id);
    }
	
	@GetMapping(value="/getUserByUsername", params= {"username"})
    public UserAccount getUserByUsername(String username) {
        return userDao.selectUserByUsername(username);
    }
	
	
	public UserAccountController() {
	}

	@Autowired
	public UserAccountController(UserAccountDao userDao) {
		super();
		this.userDao = userDao;
	}
	
}
