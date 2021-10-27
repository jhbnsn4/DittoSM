package dittoSM.service;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dittoSM.dao.UserAccountService;
import dittoSM.model.UserAccount;

@Service("userAccountService")
public class UserAccountServiceImpl implements UserAccountService {
	
	private UserAccountService userDao;
	
	@Override
	public void addAccount(UserAccount account) {
		userDao.insertAccount(account);
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
	public UserAccount getUserByUsername(String username) {
		return userDao.selectUserByUsername(username);
	}
	

/////////////////////// CONSTRUCTORS
	public UserAccountServiceImpl() {
	}

	@Autowired
	public UserAccountServiceImpl(UserAccountService userDao) {
		super();
		this.userDao = userDao;
	}
	
}
