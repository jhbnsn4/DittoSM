package dittoSM.dao;

import java.util.List;


import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import dittoSM.model.Post;
import dittoSM.model.UserAccount;

public class PostDaoImpl implements PostDao {

	private SessionFactory sesFact;

	@Override
	public void insertNewPost(Post post) {
		sesFact.getCurrentSession().save(post);
	}

	@Override
	public List<Post> selectAllPosts() {
		return sesFact.getCurrentSession().createQuery("FROM post ORDER BY created_time DESC", Post.class).list();
	}

	@Override
	public List<Post> selectPostsById(UserAccount user) {
		Query<Post> query = sesFact.getCurrentSession().createQuery("FROM post WHERE author_FK=:author_fk ORDER BY created_time", Post.class);
		query.setParameter("author_fk", user.getUserId());
		return query.list();
	}

	////////////////////// Constructors
	public PostDaoImpl() {
	}

	public PostDaoImpl(SessionFactory sesFact) {
		super();
		this.sesFact = sesFact;
	}

	////////////////////// Setters and Getters
	public SessionFactory getSesFact() {
		return sesFact;
	}
	
	@Autowired
	public void setSesFact(SessionFactory sesFact) {
		this.sesFact = sesFact;
	}

}
