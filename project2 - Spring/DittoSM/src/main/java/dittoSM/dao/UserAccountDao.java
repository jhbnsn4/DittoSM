package dittoSM.dao;

import java.util.List;

import dittoSM.model.ImageMap;
import dittoSM.model.UserAccount;

public interface UserAccountDao {
	
	void insertAccount(UserAccount account);
	void insertProfilePicture(ImageMap image);
	
	void updateAccount(UserAccount account);
	
	List<UserAccount> selectAllUsers();
	UserAccount selectUserById(int id);
	UserAccount selectUserByUsername(String username, String email);
}
