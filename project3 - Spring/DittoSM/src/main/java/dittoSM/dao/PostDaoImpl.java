package dittoSM.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dittoSM.model.Post;
import dittoSM.model.UserAccount;

@Transactional
@Repository("postDao")
public class PostDaoImpl implements PostDao {

	private SessionFactory sesFact;

	public  List<?> getLikes(UserAccount user) {

		Query<?> query2 = sesFact.getCurrentSession()
		.createSQLQuery("SELECT post_post_id FROM post_user_account WHERE likes_user_id = :userId");
		
		query2.setParameter("userId", user.getUserId());
		
		return query2.list();
	}

	@Override
	public void updatePost(Post post, UserAccount user) {
		
		
	
		System.out.println(getLikes(user));
	
		if(getLikes(user).contains(post.getPostId()) == false) {
		
		
		Query<?> query = sesFact.getCurrentSession().createSQLQuery(
				"INSERT INTO post_user_account (post_post_id, likes_user_id) VALUES ( :postId, :authorFk )");

		
		
		
		Query<?> query1 = sesFact.getCurrentSession()
				.createSQLQuery("UPDATE post SET like_num = :numLikes WHERE post_id = :postId");
		int updateInt = post.getNumLikes() + 1;
		query.setParameter("postId", post.getPostId());
		query.setParameter("authorFk", user.getUserId());
		query1.setParameter("numLikes", updateInt);
		query1.setParameter("postId", post.getPostId());

		query.executeUpdate();
		query1.executeUpdate();
		}
		
		else {
			System.out.println("_________________already liked_____________________");
		}
	}

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
			Hibernate.initialize(elem.getDittoFollowerList());
			Hibernate.initialize(elem.getDittoFollowingList());
		}
		for(Post elem: post1) {
			Hibernate.initialize(elem.getImageList());
			Hibernate.initialize(elem.getLikes());
			Hibernate.initialize(elem.getAuthorFK());
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
			Hibernate.initialize(elem.getAuthorFK());
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
