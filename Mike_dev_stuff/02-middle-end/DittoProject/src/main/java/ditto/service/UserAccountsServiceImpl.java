package ditto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ditto.model.UserAccounts;
import ditto.repo.UserAccountsDao;
import ditto.repo.UserAccountsDaoImpl;


@Service("userAccountServiceImpl")
public class UserAccountsServiceImpl implements UserAccountsService{

	UserAccountsDao myDao = new UserAccountsDaoImpl();
	
	@Override
	public void add(UserAccounts user) {
		myDao.insert(user);
		
	}

	@Override
	public UserAccounts getById(int id) {
		return myDao.selectById(id);
	}

	@Override
	public List<UserAccounts> getAllUsers() {
		return myDao.selectAll();
	}

	@Override
	public void amend(UserAccounts user) {
		myDao.update(user);
	}

	@Override
	public void remove(UserAccounts user) {
		myDao.delete(user);
	}

	//CONSTRUCTORS
	
	public UserAccountsServiceImpl() {}
	
	public UserAccountsServiceImpl(UserAccountsDao myDao) {
		super();
		this.myDao = myDao;
	}

	//GETTERS AND SETTERS
	public UserAccountsDao getMyDao() {
		return myDao;
	}

	@Autowired
	public void setMyDao(UserAccountsDao myDao) {
		this.myDao = myDao;
	}
	
	
	
	

}
