package ditto.service;

import java.util.List;


import ditto.model.UserAccounts;

public interface UserAccountsService {

	
	//CRUD METHODS
	
	//Create
	public void add(UserAccounts user);
	
	
	//Read
	public UserAccounts getById(int id);
	
	public List<UserAccounts> getAllUsers();
	
	
	//Update
	public void amend(UserAccounts user);
	
	//Delete
	public void remove(UserAccounts user);
	
}
