package dittoSM.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dittoSM.dao.PostDao;
import dittoSM.model.Post;
import dittoSM.model.UserAccount;

@Transactional
@Service("postServ")
public class PostServiceImpl implements PostService {

	private PostDao postDao;
	private UserAccountService userService;

	@Override
	public boolean addNewPost(Post post, int userid) {
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Timestamp timestamp = Timestamp.valueOf(dateTime.format(formatter));

		post.setCreatedTime(timestamp);
		UserAccount user1 = userService.getUserById(userid);

		post.setAuthorFK(user1);;

		postDao.insertNewPost(post);
		return true;
	}

	@Override
	public List<Post> findAllPosts() {
		return postDao.selectAllPosts();
	}

	@Override
	public List<Post> findAllPostsById(int userid) {
		return postDao.selectPostsById(userid);
	}

	///////// Constructor
	public PostServiceImpl() {
	}

	public PostServiceImpl(PostDao postDao) {
		super();
		this.postDao = postDao;
	}

	/////// Setters and Getters
	public PostDao getPostDao() {
		return postDao;
	}

	@Autowired
	public void setPostDao(PostDao postDao) {
		this.postDao = postDao;
	}

	@Autowired
	public void setUserDao(UserAccountService userService) {
		this.userService = userService;
	}

}
