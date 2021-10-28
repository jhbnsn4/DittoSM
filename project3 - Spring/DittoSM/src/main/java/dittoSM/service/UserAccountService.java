package dittoSM.service;

import java.util.List;

import dittoSM.model.UserAccount;

public interface UserAccountService {

	void addAccount(UserAccount account);
	
	void updateAccount(UserAccount account);
	
	List<UserAccount> getAllUsers();
	UserAccount getUserById(int id);
	public UserAccount getUserByUsername(String username, String email);
	
}
