package ditto.repo;

import java.util.List;

import ditto.model.UserAccounts;

public interface UserAccountsDao {

	//CRUD METHODS
	
	//Create
	public void insert(UserAccounts user);

	//Read
	public UserAccounts selectById(int id);
	
	public List<UserAccounts> selectAll();
	
	//Update
	public void update(UserAccounts user);
	
	
	//Delete
	public void delete(UserAccounts user);
	
}
