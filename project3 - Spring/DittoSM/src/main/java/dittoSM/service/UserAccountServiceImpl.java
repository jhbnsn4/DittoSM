package dittoSM.service;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.hash.Hashing;

import dittoSM.dao.UserAccountDao;
import dittoSM.model.UserAccount;
import dittoSM.utils.MyLogger;

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
	public void updateAccount(UserAccount account) {
		
		// Account cannot be null and its id must be 1 or greater
		if (account == null || account.getUserId() <= 0) {
			Logger log = MyLogger.getLoggerForClass(this);
			log.error("Cannot update account that is either null or has id of 0 or less");
			return;
		}
		
		userDao.updateAccount(account);

	}

	@Override
	public UserAccount getUserById(int id) {
		// Id must be 1 or greater
		if (id <= 0) {
			Logger log = MyLogger.getLoggerForClass(this);
			log.error("Cannot get account that has id of 0 or less");
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
