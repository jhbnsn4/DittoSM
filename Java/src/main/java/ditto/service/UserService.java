package ditto.service;

import ditto.model.User;

public interface UserService {
	
	public boolean createNewUser(User user);
	
	public User findUserById(int userId);
	public User findUserByUsername(User user);
}
