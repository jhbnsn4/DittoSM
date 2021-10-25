package dittoSM.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import dittoSM.dao.PostDao;
import dittoSM.model.Post;
import dittoSM.model.UserAccount;

public class PostServiceImpl implements PostService {

	private PostDao postDao;
	
	@Override
	public boolean addNewPost(Post post) {
		postDao.insertNewPost(post);
		return true;
	}

	@Override
	public List<Post> findAllPosts() {
		return postDao.selectAllPosts();
	}

	@Override
	public List<Post> findAllPostsById(UserAccount user) {
		return postDao.selectPostsById(user);
	}

	
	/////////Constructor
	public PostServiceImpl() {
	}
	public PostServiceImpl(PostDao postDao) {
		super();
		this.postDao = postDao;
	}

	///////Setters and Getters
	public PostDao getPostDao() {
		return postDao;
	}
	@Autowired
	public void setPostDao(PostDao postDao) {
		this.postDao = postDao;
	}
	
	
	
	
	

}
