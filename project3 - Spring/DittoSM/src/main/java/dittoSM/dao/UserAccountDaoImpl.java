package dittoSM.dao;

import java.util.List;

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
		
		return sesFact.getCurrentSession().createQuery("from UserAccount", UserAccount.class).list();
	}

	@Override
	public UserAccount selectUserById(int id) {
		
		return sesFact.getCurrentSession().get(UserAccount.class, id);
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
