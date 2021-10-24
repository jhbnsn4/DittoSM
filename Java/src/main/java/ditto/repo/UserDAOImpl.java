package ditto.repo;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ditto.model.User;

@Transactional
@Repository("userDao")
public class UserDAOImpl implements UserDAO {

	
	private SessionFactory sesFact;

	public boolean insertNewUser(User user) {
		sesFact.getCurrentSession().save(user);
		return true;
	}

	public User selectUserById(int userId) {
		return sesFact.getCurrentSession().get(User.class, userId);
	}

	public User selectUserByUserName(String username) {
		return sesFact.getCurrentSession().get(User.class, username);
	}
	
	
	
	////////////////////////////////////////
	public UserDAOImpl() {
	}
	
	public UserDAOImpl(SessionFactory sesFact) {
		super();
		this.sesFact = sesFact;
	}

	public SessionFactory getSession() {
		return sesFact;
	}
	
	@Autowired
	public void setSession(SessionFactory sesFact) {
		this.sesFact = sesFact;
	}
	
}
