package ditto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ditto.model.User;
import ditto.repo.UserDAO;


@Service("userServ")
public class UserServiceImpl implements UserService {

	private UserDAO userDao; 
	
	public boolean createNewUser(User user) {
		userDao.insertNewUser(user);
		return true;
	}

	public User findUserById(int userId) {
		return userDao.selectUserById(userId);
	}

	public User findUserByUsername(User user) {
		return userDao.selectUserByUserName(user.getUserName());
	}



	
	
	//////////////////////
	public UserServiceImpl() {
	}

	public UserServiceImpl(UserDAO userDao) {
		super();
		this.userDao = userDao;
	}

	public UserDAO getUserDao() {
		return userDao;
	}
	
	@Autowired
	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}
	
	
	
	
	
}
