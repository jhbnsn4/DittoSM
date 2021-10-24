package ditto.repo;

import ditto.model.User;

public interface UserDAO {
	
	public boolean insertNewUser(User user);
	
	public User selectUserById(int userId);
	public User selectUserByUserName(String username);

}
