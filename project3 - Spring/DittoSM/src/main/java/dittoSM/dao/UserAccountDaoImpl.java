package dittoSM.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dittoSM.model.ImageMap;
import dittoSM.model.Post;
import dittoSM.model.UserAccount;

/**
 * 
 * @author Ryan Moss
 * @author Mike Keefer
 * @author Jae Kyoung Lee (LJ)
 *
 */
@Transactional
@Repository("userAccountDao")
public class UserAccountDaoImpl implements UserAccountDao {

	private SessionFactory sesFact;
	
	/**
	 * Saves account via current session
	 */
	@Override
	public void insertAccount(UserAccount account) {
		sesFact.getCurrentSession().save(account);
	}
	
	/**
	 * Inserts profile picture, via the ImageMap model, and saves or updates 
	 * the current profile photo.
	 */
	@Override
	public void insertProfilePicture(ImageMap image) {
		sesFact.getCurrentSession().saveOrUpdate(image);
	}
	
	/**
	 * Update account via current session.
	 */
	@Override
	public void updateAccount(UserAccount account) {
		sesFact.getCurrentSession().update(account);
	}
	
	/**
	 * Returns a list of UserAccounts via query, and runs a hibernate.initialize to populate list.
	 */
	@Override
	public List<UserAccount> selectAllUsers() {
		
		List<UserAccount> myList = sesFact.getCurrentSession().createQuery("from UserAccount", UserAccount.class)
				.list();
		for (UserAccount e : myList) {
			Hibernate.initialize(e.getPostList());
			Hibernate.initialize(e.getDittoFollowerList());
			Hibernate.initialize(e.getDittoFollowingList());
			
			for (Post post: e.getPostList()) {
				Hibernate.initialize(post.getImageList());
				Hibernate.initialize(post.getLikes());
			}
		}
		return myList;
	}
	
	/**
	 * Retrieves the id of the current user, initializes the lazily fetched lists, and returns a UserAccount object.
	 */
	@Override
	public UserAccount selectUserById(int id) {

		UserAccount account = sesFact.getCurrentSession().get(UserAccount.class, id);
		if (account != null) {
			Hibernate.initialize(account.getPostList());
			Hibernate.initialize(account.getDittoFollowerList());
			Hibernate.initialize(account.getDittoFollowingList());
		}
		// Initialize lazily fetched proxies

		return account;
	}
	
	/**
	 * Selects a user via username of email, checks for null, initializes the lazily fetched lists, and returns a UserAccount.  
	 * This method is used for login.
	 */
	@Override
	public UserAccount selectUserByUsername(String username, String email) {
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
