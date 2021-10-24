package ditto.repo;


import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ditto.model.UserAccounts;

@Transactional
@Repository("userAccountsDaoImpl")
public class UserAccountsDaoImpl implements UserAccountsDao{
	
	private SessionFactory sesFact;
	
	
	//CRUD METHODS
	
	//Create
	@Override
	public void insert(UserAccounts user) {
		sesFact.getCurrentSession().save(user);
	}

	//Read
	@Override
	public UserAccounts selectById(int id) {
		return sesFact.getCurrentSession().get(UserAccounts.class, id);
	}
	@Override
	public List<UserAccounts> selectAll(){
		return sesFact.getCurrentSession().createQuery("from UserAccounts", UserAccounts.class).list();
	}
	
	//Update
	@Override
	public void update(UserAccounts user) {
		sesFact.getCurrentSession().merge(user);
	}
	
	
	//Delete
	@Override
	public void delete(UserAccounts user) {
		sesFact.getCurrentSession().delete(user);
	}
	
	
	
	//CONSTRUCTORS
	public UserAccountsDaoImpl() {/*No Arg*/}

	public UserAccountsDaoImpl(SessionFactory sesFact) {
		super();
		this.sesFact = sesFact;
	}

	//GETTERS AND SETTERS
	public SessionFactory getSesFact() {
		return sesFact;
	}

	@Autowired
	public void setSesFact(SessionFactory sesFact) {
		this.sesFact = sesFact;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
