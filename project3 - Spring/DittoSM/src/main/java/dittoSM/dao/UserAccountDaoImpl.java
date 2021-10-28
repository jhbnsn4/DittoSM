package dittoSM.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dittoSM.model.UserAccount;

@Transactional
@Repository("userAccountDao")
public class UserAccountDaoImpl implements UserAccountDao {

	private SessionFactory sesFact;
	
	@Override
	public void insertAccount(UserAccount account) {
		sesFact.getCurrentSession().save(account);
	}

	@Override
	public void updateAccount(UserAccount account) {
		sesFact.getCurrentSession().update(account);
	}

	@Override
	public List<UserAccount> selectAllUsers() {
		
		List<UserAccount> myList = sesFact.getCurrentSession().createQuery("from UserAccount", UserAccount.class).list();
		for(UserAccount e: myList) {
			Hibernate.initialize(e.getPostList());
			Hibernate.initialize(e.getDittoFollowerList());
			Hibernate.initialize(e.getDittoFollowingList());
		}
		return myList;
	}

	@Override
	public UserAccount selectUserById(int id) {
		
        UserAccount account = sesFact.getCurrentSession().get(UserAccount.class, id);
        
        // Initialize lazily fetched proxies
        if(account!=null) 
        {
        	Hibernate.initialize(account.getPostList());
        	Hibernate.initialize(account.getDittoFollowerList());
        	Hibernate.initialize(account.getDittoFollowingList());
        }
        
        return account;
	}
	
	@Override
	public UserAccount selectUserByUsername(String username, String email) {
		System.out.println(username);
		UserAccount account=null;
		try 
		{
			account = sesFact.getCurrentSession()
        		.createQuery("from UserAccount where username=:username or email=:email", UserAccount.class)
        		.setParameter("username", username)
        		.setParameter("email", email)
        		.uniqueResult();
		} catch(Exception e)
		{
		}
        // Initialize lazily fetched proxies
        if(account!=null) 
        {
        	Hibernate.initialize(account.getPostList());
        	Hibernate.initialize(account.getDittoFollowerList());
        	Hibernate.initialize(account.getDittoFollowingList());
        }
        
        return account;
	}
	
	
	
//////////////// CONSTRUCTORS
	public UserAccountDaoImpl() {
	}

	public UserAccountDaoImpl(SessionFactory sesFact) {
		super();
		this.sesFact = sesFact;
	}

//////////////// GETTERS & SETTERS
	public SessionFactory getSesFact() {
		return sesFact;
	}

	@Autowired
	public void setSesFact(SessionFactory sesFact) {
		this.sesFact = sesFact;
	}
	
	

}
