package dittoSM.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dittoSM.model.Post;
import dittoSM.model.UserAccount;

@Transactional
@Repository("postDao")
public class PostDaoImpl implements PostDao {

	private SessionFactory sesFact;

	@Override
	public void insertNewPost(Post post) {
		sesFact.getCurrentSession().save(post);
	}

	@Override
	public List<Post> selectAllPosts() {
		List<Post> post1 = sesFact.getCurrentSession().createQuery("FROM Post order by createdTime desc", Post.class).list();
		List<UserAccount> users = sesFact.getCurrentSession().createQuery("from UserAccount", UserAccount.class).list();
		
		for(UserAccount elem: users) {
			Hibernate.initialize(elem.getPostList());
		}
		for(Post elem: post1) {
			Hibernate.initialize(elem.getImageList());
			Hibernate.initialize(elem.getLikes());
		}
		return post1;
	}

	@Override
	public List<Post> selectPostsById(int userid) {
		UserAccount user = sesFact.getCurrentSession().get(UserAccount.class, userid);
		List<Post> post = sesFact.getCurrentSession().createQuery("FROM Post where authorFK=:authorFK order by createdTime desc", Post.class).setParameter("authorFK", user).list();
		for(Post elem: post) {
			Hibernate.initialize(elem.getImageList());
			Hibernate.initialize(elem.getLikes());
		}
		return post;
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
