package dittoSM.service;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;

import dittoSM.dao.UserAccountDao;
import dittoSM.model.ImageMap;
import dittoSM.model.UserAccount;

@Service("userAccountService")
public class UserAccountServiceImpl implements UserAccountService {
	
	private UserAccountDao userDao;
	
	@Override
	public void addAccount(UserAccount account) {
		//Encoding Password
		String hashedPassword = Hashing.sha256().hashString(account.getPassword(), StandardCharsets.UTF_8).toString();
		account.setPassword(hashedPassword);
		
		userDao.insertAccount(account);
	}
	
	@Override
	public void addProfilePicture(byte[] imgFile, UserAccount currentUser) {
		
		// Create ImageMap object
		ImageMap image = new ImageMap(imgFile, currentUser);
		userDao.insertProfilePicture(image);
	}

	@Override
	public void updateAccount(UserAccount account) {
		
		// Account cannot be null and its id must be 1 or greater
		if (account == null || account.getUserId() <= 0) {
			// TODO: log here
			return;
		}
		
		userDao.updateAccount(account);

	}

	@Override
	public UserAccount getUserById(int id) {
		// Id must be 1 or greater
		if (id <= 0) {
			//TODO: log here
			return null;
		}
		
		return userDao.selectUserById(id);
	}
	
	@Override
	public List<UserAccount> getAllUsers() {
		return userDao.selectAllUsers();
	}
	
	@Override
	public UserAccount getUserByUsername(String username, String email) {
		return userDao.selectUserByUsername(username, email);
	}
	
	
/////////////////////// CONSTRUCTORS
	public UserAccountServiceImpl() {
	}

	@Autowired
	public UserAccountServiceImpl(UserAccountDao userDao) {
		super();
		this.userDao = userDao;
	}

	
}
