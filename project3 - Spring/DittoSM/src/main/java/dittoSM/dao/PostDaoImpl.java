package dittoSM.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
	
//	public static void main(String[] args) {
//		PostDaoImpl obj = new PostDaoImpl();
//		LocalDateTime dateTime = LocalDateTime.now();
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		Timestamp timestamp = Timestamp.valueOf(dateTime.format(formatter));		
//		UserAccount user = new UserAccount(
//				1, "super", "duper", 
//				"super first name", "super last name",
//				timestamp, "this is my status"
//				);
//
//		obj.insertNewPost(new Post(
//				"this is a test",
//				0,
//				timestamp,
//				user
//				 ));
//	}

	@Override
	public void insertNewPost(Post post) {
		sesFact.getCurrentSession().save(post);
	}

	@Override
	public List<Post> selectAllPosts() {
		return sesFact.getCurrentSession().createQuery("FROM Post ORDER BY createdTime DESC", Post.class).list();
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
